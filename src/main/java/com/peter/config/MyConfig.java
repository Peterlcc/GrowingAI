package com.peter.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.peter.component.MultiLevelTaskQueue;
import com.peter.utils.RedisUtil;
import com.peter.component.TaskQueue;
import com.peter.utils.RunTag;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class MyConfig {
	private Log LOG = LogFactory.getLog(MyConfig.class);
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		WebMvcConfigurer webMvcConfigurer=new WebMvcConfigurer() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new AdminLoginHandlerInterceptor()).addPathPatterns("/admin/**")
						.excludePathPatterns("/admin/login","/admin/code");
				registry.addInterceptor(new UserLoginHandlerInterceptor()).addPathPatterns("/**")
						.excludePathPatterns("/adminSource/**","/vendor/**","/druid/**","*.ico","/img/**","/css/**","/js/**","/webjars/**",
								"/error","/test",
								"/regist","/login",
								"/user/regist","/user/code","/user/login",
								"/admin/login","/admin/code"
						);
			}

//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				LOG.info("初始化 CORSConfiguration 配置");
//				registry.addMapping("/**")
//						.allowedHeaders("*")
//						.allowedMethods("*")
//						.allowedOrigins("*")
//						.allowCredentials(true);// 允许跨域带上cookies;
//			}

			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addViewController("/").setViewName("/index");
				registry.addViewController("/home").setViewName("/index");
				registry.addViewController("/main").setViewName("/index");
				registry.addViewController("/index").setViewName("/index");
				registry.addViewController("/index.html").setViewName("/index");
				registry.addViewController("/about").setViewName("/index");
			}
			
		};
		return webMvcConfigurer;
	}
	
	//国际化处理
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    //配置文件加载
  	@Bean
  	@ConfigurationProperties(prefix = "spring.datasource")
  	public DataSource dataSource() {
  		return new DruidDataSource();
  	}
  	
  	//Druid监控Servlet
  	@Bean
  	public ServletRegistrationBean<Servlet> statViewServlet(){
  		ServletRegistrationBean<Servlet> bean = 
  				new ServletRegistrationBean<Servlet>(
  						new StatViewServlet(), "/druid/*");
  		Map<String, String> initParameters =new HashMap<String, String>();
  		initParameters.put("loginUsername","admin");
  		initParameters.put("loginPassword","cheng0526");
  		initParameters.put("allow","");//默认或者不写允许所有
  		initParameters.put("deny","192.168.1.2");
  		
  		bean.setInitParameters(initParameters);
  		return bean;
  	}
  	
  	//监控Filter
  	@Bean
  	public FilterRegistrationBean<Filter> addBean(){
  		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>();
  		bean.setFilter(new WebStatFilter());
  		Map<String, String> initParameters =new HashMap<String, String>();
  		initParameters.put("exclusions","*.js,/static/*,*.css.*.img,*.svg,*.ico,/druid/*");
  		bean.setInitParameters(initParameters);
  		bean.setUrlPatterns(Arrays.asList("/*"));
  		return bean;
  	}
  	@Bean
	public RunTag addRunTag(){
		return new RunTag();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
//		GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
	@Bean
	public RedisTemplate<String, Object> algorithmRedisTemplate(
			@Value("${spring.redis-algorithm.database}") int database,
			@Value("${spring.redis.timeout}") long timeout,
			@Value("${spring.redis.lettuce.pool.max-active}") int maxActive,
			@Value("${spring.redis.lettuce.pool.max-wait}") int maxWait,
			@Value("${spring.redis.lettuce.pool.max-idle}") int maxIdle,
			@Value("${spring.redis.lettuce.pool.min-idle}") int minIdle,
			@Value("${spring.redis-algorithm.host}") String hostName,
			@Value("${spring.redis-algorithm.port}") int port,
			@Value("${spring.redis-algorithm.password}") String password) {
		/* ========= 基本配置 ========= */
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(hostName);
		configuration.setPort(port);
		configuration.setDatabase(database);
		if (!ObjectUtils.isEmpty(password)) {
			RedisPassword redisPassword = RedisPassword.of(password);
			configuration.setPassword(redisPassword);
		}

		/* ========= 连接池通用配置 ========= */
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxTotal(maxActive);
		genericObjectPoolConfig.setMinIdle(minIdle);
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMaxWaitMillis(maxWait);

		/* ========= jedis pool ========= */
        /*
        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder builder = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        builder.connectTimeout(Duration.ofSeconds(timeout));
        builder.usePooling();
        builder.poolConfig(genericObjectPoolConfig);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration, builder.build());
        // 连接池初始化
        connectionFactory.afterPropertiesSet();
        */

		/* ========= lettuce pool ========= */
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
		builder.poolConfig(genericObjectPoolConfig);
		builder.commandTimeout(Duration.ofSeconds(timeout));
		LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, builder.build());
		factory.afterPropertiesSet();


		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
//		GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public TaskQueue taskQueue(RedisUtil redisUtil){
		TaskQueue taskQueue = new MultiLevelTaskQueue(redisUtil);
		return taskQueue;
	}
	@Bean
	public RedisUtil redisUtil(RedisTemplate<String,Object> redisTemplate){
		return new RedisUtil(redisTemplate);
	}
	@Bean
	public RedisUtil algorithmRedisUtil(RedisTemplate<String,Object> algorithmRedisTemplate){
		return new RedisUtil(algorithmRedisTemplate);
	}
}

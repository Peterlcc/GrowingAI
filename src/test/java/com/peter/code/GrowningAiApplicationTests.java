package com.peter.code;

import com.peter.bean.User;
import com.peter.component.GrowningAiConfig;
import com.peter.component.RedisUtil;
import com.peter.service.UserService;
import com.peter.utils.RegexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;

@SpringBootTest
class GrowningAiApplicationTests {

	@Autowired
	private GrowningAiConfig growningAiConfig;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		String email="123@123.com";
		String number="123456";
		System.out.println(RegexUtils.isEmail(email));
		System.out.println(RegexUtils.isMatch("\\d+",number));

		System.out.println(growningAiConfig.getTmpDir());
		System.out.println(growningAiConfig.getUploadPath());

		List<User> simpleUsers = userService.getSimpleUsers();
		redisUtil.lSet("key_test",simpleUsers.get(0));
		User key_test = (User) redisUtil.lGetIndex("key_test", 0);
		key_test.setLoginTime(new Date());
		System.out.println(key_test);
	}

}

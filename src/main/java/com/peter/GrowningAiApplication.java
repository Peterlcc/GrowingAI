package com.peter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(basePackages = {"com.peter.mapper"})
@EnableAsync
public class GrowningAiApplication {

	public static void main(String[] args) {
		//PropertyConfigurator.configure(args[0]);
		SpringApplication.run(GrowningAiApplication.class, args);
	}

}

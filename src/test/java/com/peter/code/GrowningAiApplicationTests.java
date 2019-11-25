package com.peter.code;

import com.peter.component.GrowningAiConfig;
import com.peter.utils.RegexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GrowningAiApplicationTests {

	@Autowired
	private GrowningAiConfig growningAiConfig;
	@Test
	void contextLoads() {
		String email="123@123.com";
		String number="123456";
		System.out.println(RegexUtils.isEmail(email));
		System.out.println(RegexUtils.isMatch("\\d+",number));

		System.out.println(growningAiConfig.getTmpDir());
		System.out.println(growningAiConfig.getUploadPath());
	}

}

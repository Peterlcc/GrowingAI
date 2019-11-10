package com.peter.code;

import com.peter.utils.RegexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GrowningAiApplicationTests {

	@Test
	void contextLoads() {
		String email="123@123.com";
		String number="123456";
		System.out.println(RegexUtils.isEmail(email));
		System.out.println(RegexUtils.isMatch("\\d+",number));
	}

}

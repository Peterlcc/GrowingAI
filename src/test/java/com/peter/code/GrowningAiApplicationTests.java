package com.peter.code;

import com.peter.bean.SysModel;
import com.peter.component.GrowningAiConfig;
import com.peter.mapper.SysModelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GrowningAiApplicationTests {

	@Autowired
	private GrowningAiConfig growningAiConfig;
	@Autowired
	private SysModelMapper sysModelMapper;
	@Test
	void contextLoads() {
//		String email="123@123.com";
//		String number="123456";
//		System.out.println(RegexUtils.isEmail(email));
//		System.out.println(RegexUtils.isMatch("\\d+",number));
//
//		System.out.println(growningAiConfig.getTmpDir());
//		System.out.println(growningAiConfig.getUploadPath());
		List<SysModel> sysModels = sysModelMapper.selectByExample(null);
		sysModels.forEach(sysModel -> {
			System.out.println(sysModel);
		});
	}

}

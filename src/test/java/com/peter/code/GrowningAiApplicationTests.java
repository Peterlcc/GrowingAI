package com.peter.code;

import com.peter.component.GrowningAiConfig;
import com.peter.utils.RegexUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class GrowningAiApplicationTests {

	@Autowired
	private GrowningAiConfig growningAiConfig;

	@Autowired
	private JavaMailSender mailSender;
	@Test
	void contextLoads() {
//		String email="123@123.com";
//		String number="123456";
//		System.out.println(RegexUtils.isEmail(email));
//		System.out.println(RegexUtils.isMatch("\\d+",number));
//
//		System.out.println(growningAiConfig.getTmpDir());
//		System.out.println(growningAiConfig.getUploadPath());


	}

	@Test
	void simpleMailTest(){
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("BUAA 验证码");
		simpleMailMessage.setText("1234");
		simpleMailMessage.setTo("1576167813@qq.com");
		simpleMailMessage.setFrom("peter_buaa@163.com");
		mailSender.send(simpleMailMessage);
		System.out.println("send");
	}
	@Test
	void mineMailTest() throws MessagingException {
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject("复杂邮件");
		helper.setText("<b style='color:red'>复杂消息</b>",true);
		helper.setTo("1576167813@qq.com");
		helper.setFrom("peter_buaa@163.com");

		helper.addAttachment("1.jpg",new File("/home/peter/图片/from windows/1.jpg"));
//		helper.addAttachment("2.jpg",new File("/home/peter/图片/from windows/1.jpg"));

		mailSender.send(message);
	}

}

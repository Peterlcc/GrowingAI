package com.peter.code;

import com.peter.bean.Project;
import com.peter.bean.User;
import com.peter.component.GrowningAiConfig;
import com.peter.component.RedisUtil;
import com.peter.component.TaskQueue;
import com.peter.service.ProjectService;
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

	@Autowired
	private TaskQueue taskQueue;

	@Autowired
	private ProjectService projectService;

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

	@Test
	void taskQueueTest(){
		List<Project> allSimpleProjects = projectService.getAllSimpleProjects();

		taskQueue.addTask(allSimpleProjects.get(0),false);
		taskQueue.addTask(allSimpleProjects.get(1),false);
		taskQueue.addTask(allSimpleProjects.get(2),false);
		Project task1 = taskQueue.getTask();
//		task1.setCreateTime(new Date());
		System.out.println("task1:"+task1);
		taskQueue.addTask(task1,true);
		Project task2 = taskQueue.getTask();
		System.out.println("task2:"+task2);
		Project task3 = taskQueue.getTask();
		System.out.println("task3:"+task3);
		Project task4 = taskQueue.getTask();
		System.out.println("task4:"+task4);
	}

}

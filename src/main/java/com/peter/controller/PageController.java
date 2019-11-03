package com.peter.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	private Log LOG = LogFactory.getLog(PageController.class);
	
	@GetMapping("index")
	public String index() {
		LOG.info("index.html requested!");
		return "index";
	}
	@GetMapping("login")
	public String login() {
		LOG.info("index.html requested!");
		return "user/login";
	}
	@GetMapping("list")
	public String list() {
		return "list";
	}
	@GetMapping("show")
	public String show() {
		return "show";
	}
	@GetMapping("exception")
	public String exception() {
		int i=10/0;
		return "error/404";
	}
}

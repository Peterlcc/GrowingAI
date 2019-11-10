package com.peter.error;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);
	//
	@ExceptionHandler(Exception.class)
	public String handlerException(Exception exception,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		LOG.error(exception.getMessage());
		exception.printStackTrace();
		map.put("message", exception.getMessage());
		request.setAttribute("javax.servlet.error.status_code", 404);
		request.setAttribute("ext", map);
		//接用springboot自身的客户端自适应
		return "forward:/error";
	}
}

package com.peter.controller;

import com.peter.component.SystemMailConfig;
import com.peter.message.Result;
import com.peter.message.ResultCode;
import com.peter.service.MailService;
import com.peter.utils.RegexUtils;
import com.peter.utils.VerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lcc
 * @date 2020/9/16 下午5:11
 */
@Controller
@RequestMapping("mail")
@Slf4j
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private SystemMailConfig mailConfig;
    @Autowired
    private HttpServletRequest request;

    private Result send(String email,String attr){
        if(!RegexUtils.isEmail(email)){
            log.error(email+"is not illegal");
            return Result.failure(ResultCode.EMAIL_ILLEGAL);
        }
        String mailCode = VerifyUtil.createCode();
        boolean send = mailService.send(mailConfig.getFrom(), email, mailConfig.getValicodeSubject(), "你的验证码为：" + mailCode);
        if (!send){
            log.error("failed to send "+attr+" to "+email);
            return Result.failure(ResultCode.EMAIL_SEND_FAIL);
        }
        request.getSession().setAttribute(attr,mailCode);
        log.info("sent "+attr+" to "+email);
        return Result.success("邮件已发送");
    }
    @GetMapping("send/register")
    public Result sendRegister(String email){
        return send(email,"registerCode");
    }
    @GetMapping("send/forgot")
    public Result sendForgot(String email){
        return send(email,"forgotCode");
    }
}

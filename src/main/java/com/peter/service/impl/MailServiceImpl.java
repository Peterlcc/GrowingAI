package com.peter.service.impl;

import com.peter.service.MailService;
import com.peter.utils.FileNameUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author lcc
 * @date 2020/9/16 下午4:09
 */
@Service
public class MailServiceImpl implements MailService {
    private static final Log LOG = LogFactory.getLog(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean send(String from,String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            LOG.info(String.join(" ", "send simple", to, content, "succeed"));
            return true;
        } catch (Exception e) {
            LOG.error(String.join(" ", "send simple", to, content, "failed!", e.getMessage()));
            return false;
        }
    }

    @Override
    public boolean send(String from,String to, String subject, String content,String... filePaths) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setTo(to);
            helper.setFrom(from);
            for (String filePath : filePaths) {
                helper.addAttachment(FileNameUtil.extractFileName(filePath), new File(filePath));
            }
            mailSender.send(message);
            LOG.info(String.join(" ", "send with file", to, content, "succeed"));
            return true;
        } catch (Exception e) {
            LOG.info(String.join(" ", "send with file", to, content, "failed!", e.getMessage()));
            return false;
        }
    }
}

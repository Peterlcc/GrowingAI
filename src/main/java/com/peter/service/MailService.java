package com.peter.service;

/**
 * @author lcc
 * @date 2020/9/16 下午4:07
 */
public interface MailService {
    boolean send(String from,String to,String subject,String content);
    boolean send(String from,String to,String subject,String content,String... filePath);
}

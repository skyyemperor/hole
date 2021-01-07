package com.starvel.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void send(String subject, String content, String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);//设置标题
        message.setText(content);//设置内容

        message.setTo(receiver);
        message.setFrom(sender);

        mailSender.send(message);

        System.out.println("Mail已发送： " + message);
    }

    public void sendHtml(String subject, String content, String receiver) {
        try {
            //创建一个复杂的消息邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(subject);//设置标题
            helper.setText(content, true);//设置内容

            helper.setTo(receiver);
            helper.setFrom(sender);

            mailSender.send(mimeMessage);

            System.out.println("Mail已发送： " + mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

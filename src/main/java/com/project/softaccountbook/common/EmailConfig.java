package com.project.softaccountbook.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Getter
@ConfigurationProperties(prefix = "mail")
@Configuration
public class EmailConfig {
    private final Mail mail = new Mail();

    @Getter
    @Setter
    @ToString
    public static class Mail {
        private String host;
        private int port;
        private String email;
        private String username;
        private String password;
    }

    @Bean
    public JavaMailSender mailSender() {
        log.info("mail = {}", mail);

        JavaMailSenderImpl sender = new JavaMailSenderImpl(); // JavaMailSender 구현체 생성
        sender.setHost(mail.getHost());
        sender.setPort(mail.getPort());
        sender.setUsername(mail.getUsername());
        sender.setPassword(mail.getPassword());

        Properties properties = new Properties(); // java mail 속성 지정 객체
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.debug", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.naver.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        sender.setJavaMailProperties(properties); // mail 구현체에 속성 값을 넣음

        return sender;
    }
}

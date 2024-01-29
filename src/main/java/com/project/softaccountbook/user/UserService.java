package com.project.softaccountbook.user;

import com.project.softaccountbook.common.EmailConfig;
import com.project.softaccountbook.common.Utils;
import com.project.softaccountbook.user.model.UserEmailAuthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final JavaMailSender javaMailSender;

    @Value("${mail.mail.email}")
    private String email;

    int nmChk(String nm) {
        return mapper.nmChk(nm);
    }

    int updateVisitCnt(int iuser) {
        return mapper.updateVisitCnt(iuser);
    }

    boolean emailSend(String email) {
        String from = this.email;
        String to = email;
        String randomCode = String.valueOf(UUID.randomUUID()).substring(0, 5);

        String title = "말랑이 가계부 회원가입 인증 이메일입니다.";
        StringBuilder content_ = new StringBuilder();
        content_.append("<h2>말랑이 가계부를 방문해주셔서 감사합니다.</h2>");
        content_.append("인증 코드는<strong>" + randomCode + "</strong>입니다.");
        content_.append("<br>");
        content_.append("이메일 인증 창에 이메일 인증 코드를 입력해주세요.");
        String content = String.valueOf(content_);

        MimeMessage mm = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mm, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content, true);
            javaMailSender.send(mm);

            UserEmailAuthDto dto = new UserEmailAuthDto();
            dto.setEmail(to);
            dto.setEmailAuthCode(randomCode);

            return Utils.isNotNull(insertEmailAuthCode(dto)) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    int insertEmailAuthCode(UserEmailAuthDto dto) {
        return mapper.insertEmailAuthCode(dto);
    }

    int emailChk(String email) {
        return mapper.emailChk(email);
    }

    int emailAuthCodeChk(UserEmailAuthDto dto) {
        return mapper.emailAuthCodeChk(dto);
    }
}

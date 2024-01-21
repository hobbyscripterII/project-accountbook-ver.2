package com.project.softaccountbook.user;

import com.project.softaccountbook.user.model.UserEmailSignInDto;
import com.project.softaccountbook.user.model.UserEmailSignInVo;
import com.project.softaccountbook.user.model.UserSignUpDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest // @Mapper로 등록한 인터페이스를 bean으로 등록함
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트 시 DB 교체를 하지 않음(어노테이션 제거 시 H2로 테스트 진행)
class UserMapperTest {
    // 테스트는 각각 다르게 실행되며 실행 순서를 보장할 수 없음
    private UserSignUpDto signUpDto;
    @Autowired private UserMapper mapper;

    @Test
    @DisplayName("회원가입")
    void signUp() {
        String email = "jy@gmail.com";
        String nm = "이주영";
        String upw = "test!";
        String now = String.valueOf(LocalDate.now());

        this.signUpDto = new UserSignUpDto();
        signUpDto.setEmail(email);
        signUpDto.setUpw(upw);
        signUpDto.setNm(nm);
        signUpDto.setGender('W');
        signUpDto.setAge(31);
        signUpDto.setFirstCreatedAt(now);

        int signUp = mapper.signUp(signUpDto);
        assertEquals(1, signUp);
    }

    @Test
    @DisplayName("회원탈퇴")
    void withdrawal() {
        signUp();
        int withdrawal = mapper.withdrawal(signUpDto.getIuser());
        assertEquals(1, withdrawal);
    }

    @Test
    @DisplayName("로그인")
    void emailSignIn() {
        signUp(); // 테스트는 독립적으로 실행되므로 로그인 테스트 실행 전 회원가입을 미리 실시함

        UserEmailSignInDto signInDto = new UserEmailSignInDto();
        signInDto.setEmail(signUpDto.getEmail());
        signInDto.setUpw(signUpDto.getUpw());

        UserEmailSignInVo vo = mapper.emailSignIn(signInDto);
        assertNotNull(vo);
        System.out.printf("vo = {%s}\n", vo);
    }
}
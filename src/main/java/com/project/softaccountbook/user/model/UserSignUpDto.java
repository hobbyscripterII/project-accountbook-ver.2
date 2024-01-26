package com.project.softaccountbook.user.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Slf4j
@Data
public class UserSignUpDto {
    private int iuser;
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대/소문자, 숫자, 특수문자를 입력해주세요.")
    private String upw;
    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 7, message = "닉네임은 2~7자 내외로 입력해주세요.")
    private String nm;
    @NotNull(message = "성별을 선택해주세요.") // @NotEmpty는 문자열만 가능, 라디오 버튼은 @NotNull 사용
    private char gender;
//    private int age;
    @NotEmpty(message = "생년월일을 선택해주세요.")
    private String birth;
    private String firstCreatedAt;
    private int firstCreatedUser;

//    public int setAge() {
//        return (LocalDate.now().getYear() - Integer.parseInt(birth.substring(0, 4)));
//    }
}
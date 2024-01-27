package com.project.softaccountbook.user;

import com.project.softaccountbook.user.model.UserEmailAuthDto;
import com.project.softaccountbook.user.model.UserEmailSignInDto;
import com.project.softaccountbook.user.model.UserEmailSignInVo;
import com.project.softaccountbook.user.model.UserSignUpDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 1. 회원가입 (14세 미만은 회원가입 불가)
    int signUp(UserSignUpDto dto);

    // 2. 이메일 로그인
    UserEmailSignInVo emailSignIn(UserEmailSignInDto dto);

    // 3. 로그인 시 방문 횟수 ++
    int updateVisitCnt(int iuser);

    // 4. 회원 수정 (닉네임 중복 불가)

    // 5. 회원탈퇴(TDD 전용)
    int withdrawal(int iuser);

    // 6. 닉네임 중복 체크
    int nmChk(String nm);

    int insertEmailAuthCode(UserEmailAuthDto dto);

    int emailChk(String email);

    int emailAuthCodeChk(UserEmailAuthDto dto);
}

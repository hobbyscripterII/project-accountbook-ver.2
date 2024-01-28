package com.project.softaccountbook.user;

import com.project.softaccountbook.common.Utils;
import com.project.softaccountbook.user.model.UserEmailAuthDto;
import com.project.softaccountbook.user.model.UserEmailSignInDto;
import com.project.softaccountbook.user.model.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        // 빈 값을 넘겨두면 유효성 검증 실패시 사용자가 입력한 데이터 노출 가능
        model.addAttribute("dto", new UserSignUpDto());
        return "/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute(name = "dto") UserSignUpDto dto, BindingResult bindingResult) {
        int nmChk = service.nmChk(dto.getNm());
        int emailChk = service.emailChk(dto.getEmail());

        // 닉네임 중복 검사
        if (Utils.isNotNull(nmChk)) {
            bindingResult.addError(new FieldError("dto", "nm", "이미 등록된 닉네임입니다. 다른 닉네임을 입력해주세요."));
        }

        // 이메일 중복 검사
        if (Utils.isNotNull(emailChk)) {
            bindingResult.addError(new FieldError("dto", "email", "이미 등록된 이메일입니다. 다른 이메일을 입력해주세요."));
        }

        if (bindingResult.hasErrors()) {
            return "/sign-up";
        }

        return null; // security, JWT 적용 후 추가 예정
    }

    @GetMapping("/sign-in")
    public String signIn(Model model) {
        model.addAttribute("dto", new UserEmailSignInDto());
        return "/sign-in";
    }

//    @PostMapping("/sign-in")
//    public String signIn(@ModelAttribute UserEmailSignInDto dto) {
//        log.info("spring security 접근 성공");
//        return "/";
//    }

    @GetMapping("/sign-out")
    public String signOut() {
        return "/home";
    }

    // 닉네임 중복 검사
    @GetMapping("/nm-chk")
    @ResponseBody
    public int nmChk(@RequestParam(name = "nm") String nm) { // @RequestParam name 속성 생략 시 spring security doFilter에 의해 에러 발생
        return service.nmChk(nm);
    }

    // 이메일 발송
    @GetMapping("/email/send")
    @ResponseBody
    public int emailSend(@RequestParam(name = "email") String email) {
        boolean emailSend = service.emailSend(email);
        int emailChk = service.emailChk(email);
        if (Utils.isNotNull(emailChk)) { // 이메일 중복
            return 2;
        } else if (!Utils.isNotNull(emailSend)) { // 이메일 전송 실패
            return 1;
        }
        return 0;
    }

    // 이메일 인증 코드 확인
    @PostMapping("/email/auth-chk")
    @ResponseBody
    public int emailAuthCodeChk(@RequestBody UserEmailAuthDto dto) { // json으로 받을 때 @RequestBody 명시
        int emailAuthCodeChk = service.emailAuthCodeChk(dto);

        if (Utils.isNotNull(emailAuthCodeChk)) {
            return 1;
        } else {
            return 0;
        }
    }
}

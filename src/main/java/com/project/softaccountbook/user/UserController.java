package com.project.softaccountbook.user;

import com.project.softaccountbook.common.Utils;
import com.project.softaccountbook.user.model.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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

        // 닉네임 중복 검사
        if(Utils.isNotNull(nmChk)) {
            bindingResult.addError(new FieldError("dto", "nm", "이미 등록된 닉네임입니다. 다른 닉네임을 입력해주세요."));
        }

        if (bindingResult.hasErrors()) {
            return "/sign-up";
        }
        return null;
    }

    @GetMapping("/sign-in")
    public String signIn() {
        return "/sign-in";
    }

    @GetMapping("/sign-out")
    public String signOut() {
        return "/home";
    }

    // 닉네임 중복 검사
    @GetMapping("/nm-chk")
    @ResponseBody
    public int nmChk(@RequestParam String nm) {
        return service.nmChk(nm);
    }
}

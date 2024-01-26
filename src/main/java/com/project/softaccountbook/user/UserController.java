package com.project.softaccountbook.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/sign-up")
    public String signUp() {
        return "/sign-up";
    }

    @GetMapping("/sign-in")
    public String signIn() {
        return "/sign-in";
    }

    @GetMapping("/sign-out")
    public String signOut() {
        return "/home";
    }
}

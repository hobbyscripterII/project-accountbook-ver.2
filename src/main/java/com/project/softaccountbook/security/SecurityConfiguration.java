package com.project.softaccountbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration { // 스프링 컨테이너가 호출함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // HttpSecurity - http 요청 보안 설정을 위한 핵심 객체
        http
                .csrf(c -> c.disable()) // 추후 jwt 토큰 추가

                // 로그인 페이지 커스텀
                .formLogin(f -> f // 인증 방법을 폼 로그인 방식으로 지정
                        .loginPage("/sign-in") // 로그인 페이지(html) 경로 지정
                        .loginProcessingUrl("/process-login") // url 호출 시 security가 낚아 챔, controller에 구현할 필요 x
                        .failureUrl("/sign-in?error") // 인증 실패 시 호출되는 url
                        .defaultSuccessUrl("/") // 인증 완료 후 호출되는 url
                        .permitAll())
                .exceptionHandling(e -> e
                        .accessDeniedPage("/error/403")) // 예외 발생 시 접근 제한 페이지 설정
                .authorizeHttpRequests(a -> a // 접근 권한 확인 및 접근 허용 여부 결정
                        .requestMatchers(
                                "/",
                                "/css/**", "/js/**", "/img/**", // resource
                                "/sign-up",
                                "/sign-in",
                                "/sign-out",
                                "/process-login",
                                "/email/**", // 이메일 전송 / 이메일 인증코드 확인
                                "/nm-chk", // 닉네임 중복 체크
                                "/error/**"
                        ).permitAll() // 인가 필요 x
                        .anyRequest()
                        .authenticated());
//                        .addFilterBefore();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        // db 사용 x 메모리에 회원 정보 저장
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("test")
                .password("test")
                .roles("USER")
                .build();

        // InMemoryUserDetailsManager - db 사용 x 메모리 사용 o 테스트 환경 / 데모 환경에서 사용됨
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }
}

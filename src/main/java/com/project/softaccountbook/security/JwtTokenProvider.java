//package com.project.softaccountbook.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.softaccountbook.common.AppProperties;
//import com.project.softaccountbook.user.model.MyPrincipal;
//import io.jsonwebtoken.*;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Date;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtTokenProvider {
//    private final ObjectMapper objectMapper;
//    private final AppProperties appProperties;
//    private SecretKeySpec secretKeySpec;
//
//    @PostConstruct
//    public void init() {
//        this.secretKeySpec = new SecretKeySpec(appProperties.getJwt().getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());
//    }
//
//    public String generateRefreshToken(MyPrincipal myPrincipal) {
//        return generateToken(myPrincipal, appProperties.getJwt().getRefreshTokenExpiry());
//    }
//
//    public String generateAccessToken(MyPrincipal myPrincipal) {
//        return generateToken(myPrincipal, appProperties.getJwt().getAccessTokenExpiry());
//    }
//
//    private String generateToken(MyPrincipal myPrincipal, long tokenValidMs) {
//        return Jwts.builder()
//                .claims(createClaims(myPrincipal))
//                .issuedAt(new Date(System.currentTimeMillis())) // 발행 시간
//                .expiration(new Date(System.currentTimeMillis() + tokenValidMs)) // 만료 시간
//                .signWith(secretKeySpec)
//                .compact();
//    }
//
//    private Claims createClaims(MyPrincipal myPrincipal) {
//        try {
//            String json = objectMapper.writeValueAsString(myPrincipal);
//
//            return Jwts
//                    .claims()
//                    .add("user", json)
//                    .build();
//        } catch(Exception e) {
//            return null;
//        }
//    }
//}

//package com.project.softaccountbook.security;
//
//import com.project.softaccountbook.user.model.MyPrincipal;
//import lombok.Builder;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//@Data
//@Builder
//public class MyUserDetails implements UserDetails {
//    // 요청 시 인증 및 pk 가져올 때 쓰는 객체
//    private MyPrincipal myPrincipal;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() { // 계정 만료 여부
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() { // 계정 잠김 여부
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() { // 계정 비밀번호 만료 여부
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() { // 계정 활성화 여부
//        return false;
//    }
//}

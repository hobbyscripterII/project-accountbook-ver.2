package com.project.softaccountbook.user.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPrincipal { // 토큰 저장용 객체
    private int iuser;
}

package com.project.softaccountbook.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserEmailSignInDto {
    private String email;
    private String upw;
}

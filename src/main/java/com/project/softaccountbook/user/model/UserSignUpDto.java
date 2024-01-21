package com.project.softaccountbook.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {
    private int iuser;
    private String email;
    private String upw;
    private String nm;
    private char gender;
    private int age;
    private String firstCreatedAt;
    private int firstCreatedUser;
}
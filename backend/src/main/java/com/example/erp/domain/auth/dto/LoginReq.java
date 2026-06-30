package com.example.erp.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginReq {

    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    public LoginReq(){}

    public LoginReq(String loginId, String password){
        this.loginId = loginId;
        this.password = password;
    }

}

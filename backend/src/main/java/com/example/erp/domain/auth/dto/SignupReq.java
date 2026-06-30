package com.example.erp.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupReq {

    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8,message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message ="비밀번호는 대문자와 특수문자를 포함해야 합니다." )
    private String password;

    @NotBlank(message = "이름을 입력하세요")
    private String name;

    public SignupReq() {}

    public SignupReq(String loginId, String password, String name){
        this.loginId=loginId;
        this.password = password;
        this.name= name;
    }

}

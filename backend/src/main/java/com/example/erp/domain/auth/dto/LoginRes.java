package com.example.erp.domain.auth.dto;

import lombok.Getter;

@Getter
public class LoginRes {

    private final String accessToken;
    private final Long memberId;
    private final String role;
    private final Long tenantId;


    public LoginRes(String accessToken, Long memberId, String role, Long tenantId) {
        this.accessToken = accessToken;
        this.memberId = memberId;
        this.role = role;
        this.tenantId = tenantId;
    }

}

package com.example.erp.global.jwt;

public class JwtMemberInfo {
    private final Long memberId;
    private final Long tenantId;


    public JwtMemberInfo(Long memberId, Long tenantId) {
        this.memberId = memberId;
        this.tenantId = tenantId;
    }

    public Long getMemberId(){
        return memberId;
    }

    public Long getTenantId(){
        return tenantId;
    }
}

package com.example.erp.domain.auth.dto;

import com.example.erp.domain.auth.entity.Member;
import lombok.Getter;

@Getter
public class MemberRes {

    private final Long id;
    private final String loginId;
    private final String name;
    private final String role;
    private final String status;
    private final Long tenantId;


    public MemberRes(Long id, String loginId, String name, String role, String status, Long tenantId) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.role = role;
        this.status = status;
        this.tenantId = tenantId;
    }

    public static MemberRes from(Member member){
        return new MemberRes(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getRole().name(),
                member.getStatus().name(),
                member.getTenantId()
        );
    }


}

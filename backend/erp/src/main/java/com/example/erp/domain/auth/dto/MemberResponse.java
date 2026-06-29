package com.example.erp.domain.auth.dto;

import com.example.erp.domain.auth.entity.Member;

public record MemberResponse(
        Long id,
        String loginId,
        String name,
        String role,
        String status,
        Long tenantId
) {
    public static MemberResponse from (Member member){
        return new MemberResponse(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getRole().name(),
                member.getStatus().name(),
                member.getTenantId()
        );
    }
}

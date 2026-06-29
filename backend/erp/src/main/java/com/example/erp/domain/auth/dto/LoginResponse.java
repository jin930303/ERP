package com.example.erp.domain.auth.dto;

public record LoginResponse(
        String accessToken,
        Long memberId,
        String role,
        Long tenantId
) {
}

package com.example.erp.domain.auth.dto;

import jakarta.validation.constraints.NotNull;

public record ApproveRequest(
        @NotNull Long tenantId
) {
}

package com.example.erp.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ApproveReq {

    @NotNull(message = "고객사 ID를 입력하세요.")
    private Long tenantId;

    public ApproveReq(){}

    public ApproveReq(Long tenantId) {
        this.tenantId = tenantId;
    }


}

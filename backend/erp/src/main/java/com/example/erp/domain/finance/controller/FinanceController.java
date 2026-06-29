package com.example.erp.domain.finance.controller;

import com.example.erp.domain.finance.dto.FinanceTransactionResponse;
import com.example.erp.domain.finance.service.FinanceService;
import com.example.erp.global.jwt.JwtMemberInfo;
import com.example.erp.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    //전표 조회 - ADMIN/ MANAGER 이상
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<List<FinanceTransactionResponse>>> getTransactions(
            @AuthenticationPrincipal JwtMemberInfo memberInfo) {

        List<FinanceTransactionResponse> response = financeService.getTransactions(memberInfo.tenantId());
        return ResponseEntity.ok(ApiResponse.ok(response));

    }
}

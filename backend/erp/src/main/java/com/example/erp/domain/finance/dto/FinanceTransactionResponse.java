package com.example.erp.domain.finance.dto;

import com.example.erp.domain.finance.entity.FinanceTransaction;

import java.time.LocalDateTime;

public record FinanceTransactionResponse(
        Long id,
        Long orderId,
        String type,
        Integer amount,
        LocalDateTime issuedAt
) {
    public static FinanceTransactionResponse from(FinanceTransaction transaction){
        return new FinanceTransactionResponse(
                transaction.getId(),
                transaction.getOrderId(),
                transaction.getType().name(),
                transaction.getAmount(),
                transaction.getIssuedAt()
        );
    }
}

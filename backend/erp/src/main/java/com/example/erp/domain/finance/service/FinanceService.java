package com.example.erp.domain.finance.service;

import com.example.erp.domain.finance.dto.FinanceTransactionResponse;
import com.example.erp.domain.finance.entity.FinanceTransaction;
import com.example.erp.domain.finance.entity.TransactionType;
import com.example.erp.domain.finance.repository.FinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinanceRepository financeRepository;

    //전표 조회
    @Transactional(readOnly = true)
    public List<FinanceTransactionResponse> getTransactions(Long tenantId) {
        return financeRepository.findAllByTenantId(tenantId)
                .stream()
                .map(FinanceTransactionResponse::from)
                .toList();
    }

    //전표 발행 (주문 등록 시 - DEBIT)
    @Transactional
    public void issueDebit(Long orderId, Long tenantId, Integer amount) {
        FinanceTransaction transaction = FinanceTransaction.builder()
                .orderId(orderId)
                .tenantId(tenantId)
                .type(TransactionType.DEBIT)
                .amount(amount)
                .build();

        financeRepository.save(transaction);

    }

    //역전표 발행 (주문 취소 시 - CREDIT)
    @Transactional
    public void issueCredit(Long orderId, Long tenantId, Integer amount) {
        FinanceTransaction transaction = FinanceTransaction.builder()
                .orderId(orderId)
                .tenantId(tenantId)
                .type(TransactionType.CREDIT)
                .amount(amount)
                .build();

        financeRepository.save(transaction);
    }
}

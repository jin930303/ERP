package com.example.erp.domain.finance.repository;

import com.example.erp.domain.finance.entity.FinanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRepository extends JpaRepository<FinanceTransaction, Long> {
    List<FinanceTransaction> findAllByTenantId(Long tenantId);
}

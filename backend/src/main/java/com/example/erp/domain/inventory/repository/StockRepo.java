package com.example.erp.domain.inventory.repository;

import com.example.erp.domain.inventory.entity.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepo extends JpaRepository<Stock, Long> {

    List<Stock> findAllByTenantId(Long tenantId);

    // 재고 차감/입고용 - 비관적 락으로 동시성 제어
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Stock s WHERE s.product.id = :productId AND s.tenantId = :tenantId")
    Optional<Stock> findByProductIdAndTenantIdWithLock(@Param("productId") Long productId, @Param("tenantId") Long tenantId);
}

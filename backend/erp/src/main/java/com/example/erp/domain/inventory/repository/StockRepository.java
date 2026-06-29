package com.example.erp.domain.inventory.repository;

import com.example.erp.domain.inventory.entity.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    //재고 조회용 - 락 없음
    List<Stock> findAllByTenantId(Long tenantId);

    //재고 차감용 - 비관적 락
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROm Stock s WHERE s.product.id = :productId AND s.tenantId = :tenantId")
    Optional<Stock> findByProductAndTenantIdWithLock(@Param("productId") Long productId, @Param("tenantId") Long tenantId);
}

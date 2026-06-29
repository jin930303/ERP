package com.example.erp.domain.order.repository;

import com.example.erp.domain.order.dto.OrderResponse;
import com.example.erp.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByIdAndTenantId(Long Id, Long tenantId);

    List<Order> findAllByTenantIdAndDeletedFalse(Long tenantId);
}

package com.example.erp.domain.inventory.repository;

import com.example.erp.domain.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndDeletedFalse(Long id);
}

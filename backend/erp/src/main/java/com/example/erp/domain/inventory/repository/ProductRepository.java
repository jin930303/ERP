package com.example.erp.domain.inventory.repository;

import com.example.erp.domain.inventory.entity.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Long> {

    Optional<Product> findByIdAndDeletedFalse(@NotNull Long id);
}

package com.example.erp.domain.inventory.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public Product(String name, Integer price, Long tenantId){
        this.name = name;
        this.price = price;
        this.tenantId = tenantId;
    }

    public void delete(){
        this.deleted = true;
    }
}

package com.example.erp.domain.inventory.entity;

import com.example.erp.global.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stocks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Long tenantId;

    @Builder
    public Stock(Product product, Integer quantity, Long tenantId){
        this.product = product;
        this.quantity = quantity;
        this.tenantId = tenantId;
    }

    //재고 차감
    public void decrease(int amount){
        if(this.quantity < amount){
            throw new OutOfStockException();
        }
        this.quantity -= amount;
    }

    // 입고/ 주문 취소 시 재고 복구
    public void increase(int amount){
        this.quantity += amount;
    }


}

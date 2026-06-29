package com.example.erp.domain.order.entity;

import com.example.erp.domain.inventory.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer unitPrice;

    @Builder
    public OrderItem(Order order, Product product, Integer quantity, Integer unitPrice){
        this.order=order;
        this.product=product;
        this.quantity=quantity;
        this.unitPrice=unitPrice;
    }

    public int getTotalPrice(){
        return this.unitPrice * this.quantity;
    }

}

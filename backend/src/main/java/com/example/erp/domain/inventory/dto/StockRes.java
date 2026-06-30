package com.example.erp.domain.inventory.dto;

import com.example.erp.domain.inventory.entity.Stock;
import lombok.Getter;

@Getter
public class StockRes {

    private final Long productId;
    private final String productName;
    private final Integer price;
    private final Integer quantity;

    public StockRes(Long productId, String productName, Integer price, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public static StockRes from(Stock stock){
        return new StockRes(
                stock.getProduct().getId(),
                stock.getProduct().getName(),
                stock.getProduct().getPrice(),
                stock.getQuantity()
        );
    }
}

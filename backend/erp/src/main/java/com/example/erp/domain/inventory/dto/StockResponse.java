package com.example.erp.domain.inventory.dto;

import com.example.erp.domain.inventory.entity.Stock;

public record StockResponse(
        Long productId,
        String productName,
        Integer price,
        Integer quantity
) {
    public static StockResponse from(Stock stock){
        return new StockResponse(
                stock.getProduct().getId(),
                stock.getProduct().getName(),
                stock.getProduct().getPrice(),
                stock.getQuantity()
        );
    }
}

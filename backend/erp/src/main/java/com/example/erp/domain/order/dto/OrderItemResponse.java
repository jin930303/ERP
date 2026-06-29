package com.example.erp.domain.order.dto;

import com.example.erp.domain.order.entity.OrderItem;

public record OrderItemResponse(
        Long productId,
        String productName,
        Integer quantity,
        Integer unitPrice,
        Integer totalPrice
) {
    public static OrderItemResponse from(OrderItem item){
        return new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice()
        );
    }
}

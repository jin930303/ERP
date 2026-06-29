package com.example.erp.domain.order.dto;

import com.example.erp.domain.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String status,
        LocalDateTime orderedAt,
        List<OrderItemResponse> items,
        Integer totalPrice
) {

    public static OrderResponse from(Order order){
        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(OrderItemResponse :: from)
                .toList();

        int totalPrice = items.stream()
                .mapToInt(OrderItemResponse :: totalPrice)
                .sum();

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getOrderedAt(),
                items,
                totalPrice
        );
    }
}

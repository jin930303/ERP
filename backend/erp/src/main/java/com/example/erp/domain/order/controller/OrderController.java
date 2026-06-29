package com.example.erp.domain.order.controller;

import com.example.erp.domain.order.dto.OrderRequest;
import com.example.erp.domain.order.dto.OrderResponse;
import com.example.erp.domain.order.service.OrderService;
import com.example.erp.global.jwt.JwtMemberInfo;
import com.example.erp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 등록
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @RequestBody @Valid OrderRequest request,
            @AuthenticationPrincipal JwtMemberInfo memberInfo){

        OrderResponse response = orderService.createOrder(request,memberInfo);
        return ResponseEntity.ok(ApiResponse.ok("주문이 완료되었습니다.",response));
    }

    //주문 취소
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal JwtMemberInfo memberInfo
    ){
        orderService.cancelOrder(orderId,memberInfo);
        return ResponseEntity.ok(ApiResponse.ok("주문이 취소되었습니다.",null));
    }

    //주문 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders(
            @AuthenticationPrincipal JwtMemberInfo memberInfo
    ){
        List<OrderResponse> response = orderService.getOrders(memberInfo);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }


}

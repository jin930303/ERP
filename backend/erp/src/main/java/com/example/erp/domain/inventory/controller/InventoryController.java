package com.example.erp.domain.inventory.controller;

import com.example.erp.domain.inventory.dto.RestockRequest;
import com.example.erp.domain.inventory.dto.StockResponse;
import com.example.erp.domain.inventory.service.InventoryService;
import com.example.erp.global.jwt.JwtMemberInfo;
import com.example.erp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    //재고 조회 (user 이상)
    @GetMapping
    public ResponseEntity<ApiResponse<List<StockResponse>>> getStocks(
            @AuthenticationPrincipal JwtMemberInfo memberInfo){
        List<StockResponse> response = inventoryService.getStocks(memberInfo.tenantId());
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    //입고 등록 - admin, manager 이상
    @PostMapping("/restock")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<Void>> restock(
            @RequestBody @Valid RestockRequest request,
            @AuthenticationPrincipal JwtMemberInfo memberInfo){

        inventoryService.restock(request,memberInfo.tenantId());
        return ResponseEntity.ok(ApiResponse.ok("입고가 완료되었습니다.",null));
    }

}

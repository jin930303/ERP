package com.example.erp.domain.inventory.controller;

import com.example.erp.domain.inventory.dto.RestockReq;
import com.example.erp.domain.inventory.dto.StockRes;
import com.example.erp.domain.inventory.service.InventoryService;
import com.example.erp.global.jwt.JwtMemberInfo;
import com.example.erp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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

    // 재고 조회 - 로그인한 모든 사용자 (USER 이상) 가능
    @GetMapping
    public ResponseEntity<ApiResponse<List<StockRes>>>getStocks(@AuthenticationPrincipal JwtMemberInfo memberInfo){

        List<StockRes> res = inventoryService.getStocks(memberInfo.getTenantId());
        return ResponseEntity.ok(ApiResponse.ok(res));
    }

    // 입고 등록 - 일반 사용자의 임의 재고 조작 차단을 위해 ADMIN/ MANAGER 이상만 허용
    @PostMapping("/restock")
    @PreAuthorize("hasRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<Void>>restock(@AuthenticationPrincipal JwtMemberInfo memberInfo,
                                                    @RequestBody @Valid RestockReq req){

        inventoryService.restock(req, memberInfo.getTenantId());
        return ResponseEntity.ok(ApiResponse.ok("입고가 완료되었습니다.",null));
    }

}

package com.example.erp.domain.inventory.service;

import com.example.erp.domain.inventory.dto.RestockReq;
import com.example.erp.domain.inventory.dto.StockRes;
import com.example.erp.domain.inventory.entity.Stock;
import com.example.erp.domain.inventory.repository.StockRepo;
import com.example.erp.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StockRepo stockRepo;

    //재고 조회
    @Transactional(readOnly = true)
    public List<StockRes> getStocks(Long tenantId) {
        return stockRepo.findAllByTenantId(tenantId).stream().map(StockRes::from)
                .toList();

    }

    //입고 등록
    @Transactional
    public void restock(RestockReq req, Long tenantId) {

        Stock stock = stockRepo.findByProductIdAndTenantIdWithLock(req.getProductId(),tenantId)
                .orElseThrow(()-> new EntityNotFoundException("재고 정보를 찾을 수 없습니다."));
        stock.increase(req.getQuantity());
    }
}

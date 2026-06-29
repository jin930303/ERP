package com.example.erp.domain.inventory.service;

import com.example.erp.domain.inventory.dto.RestockRequest;
import com.example.erp.domain.inventory.dto.StockResponse;
import com.example.erp.domain.inventory.entity.Stock;
import com.example.erp.domain.inventory.repository.ProductRepository;
import com.example.erp.domain.inventory.repository.StockRepository;
import com.example.erp.global.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StockRepository stockRepository;


    //재고 전체 조회
    @Transactional(readOnly = true)
    public List<StockResponse> getStocks(Long tenantId) {
        return stockRepository.findAllByTenantId(tenantId).stream().map(StockResponse :: from).toList();

    }

    //입고 등록
    @Transactional
    public void restock(@Valid RestockRequest request, Long tenantId) {
        Stock stock = stockRepository.findByProductAndTenantIdWithLock(
                request.productId(),tenantId).orElseThrow(()-> new EntityNotFoundException("재고 정보를 찾을 수 없습니다."));

        stock.increase(request.quantity());

    }
}

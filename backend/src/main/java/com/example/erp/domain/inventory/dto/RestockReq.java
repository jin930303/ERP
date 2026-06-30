package com.example.erp.domain.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RestockReq {

    @NotNull(message = "상품 ID를 입력하세요.")
    private Long productId;

    @Min(value = 1,message = "수량은 1 이상이어야 합니다.")
    private  Integer quantity;

    public RestockReq(){
    }

    public RestockReq(Long productId, Integer quantity){
        this.productId = productId;
        this.quantity = quantity;
    }
}

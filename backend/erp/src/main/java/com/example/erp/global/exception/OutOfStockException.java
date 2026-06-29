package com.example.erp.global.exception;

import org.springframework.http.HttpStatus;

public class OutOfStockException extends BusinessException{
    public OutOfStockException(){
        super("재고가 부족합니다.", HttpStatus.CONFLICT);
    }
}

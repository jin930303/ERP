package com.example.erp.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException{
    public UnauthorizedException(){
        super("인증이 필요합니다.", HttpStatus.UNAUTHORIZED);
    }
}

package com.example.erp.domain.auth.controller;

import com.example.erp.domain.auth.dto.LoginReq;
import com.example.erp.domain.auth.dto.LoginRes;
import com.example.erp.domain.auth.dto.SignupReq;
import com.example.erp.domain.auth.service.AuthService;
import com.example.erp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //회원 가입 - 가입 직후 PENDING 상태, 관리자 승인 전까지 로그인 불가
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid SignupReq req){
        authService.signup(req);
        return ResponseEntity.ok(ApiResponse.ok("회원가입이 완료되었습니다. 관리자 승인 후 로그인 가능합니다.",null));
    }

    //로그인
    public ResponseEntity<ApiResponse<LoginRes>> login(@RequestBody @Valid LoginReq req){
        LoginRes res = authService.login(req);
        return ResponseEntity.ok(ApiResponse.ok(res));
    }
}

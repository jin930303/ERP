package com.example.erp.domain.auth.controller;

import com.example.erp.domain.auth.dto.LoginRequest;
import com.example.erp.domain.auth.dto.LoginResponse;
import com.example.erp.domain.auth.dto.SignupRequest;
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

    @RequestMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid SignupRequest request){
        authService.signup(request);
        return ResponseEntity.ok(ApiResponse.ok("회원가입이 완료되었습니다.",null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request){
        LoginResponse response =authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

}

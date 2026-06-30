package com.example.erp.domain.auth.controller;

import com.example.erp.domain.auth.dto.ApproveReq;
import com.example.erp.domain.auth.dto.MemberRes;
import com.example.erp.domain.auth.entity.Member;
import com.example.erp.domain.auth.service.AuthService;
import com.example.erp.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AuthService authService;

    //승인 대기 중인 회원 목록 조회
    @GetMapping("/members/pending")
    public ResponseEntity<ApiResponse<List<MemberRes>>> getPendingMembers(){
        List<MemberRes> res = authService.getPendingMembers();

        return ResponseEntity.ok(ApiResponse.ok(res));
    }

    // 회원 승인 + 고객사 ID 할당
    @PostMapping("/members/{memberId}/approve")
    public ResponseEntity<ApiResponse<Void>>approveMember(
            @PathVariable Long memberId,
            @RequestBody @Valid ApproveReq req
            ){
        authService.approveMember(memberId,req);
        return ResponseEntity.ok(ApiResponse.ok("회원이 승인되었습니다.",null));
    }
}

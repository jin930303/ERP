package com.example.erp.domain.auth.service;

import com.example.erp.domain.auth.dto.*;
import com.example.erp.domain.auth.entity.Member;
import com.example.erp.domain.auth.entity.MemberStatus;
import com.example.erp.domain.auth.entity.Role;
import com.example.erp.domain.auth.repository.MemberRepo;
import com.example.erp.global.exception.BusinessException;
import com.example.erp.global.exception.EntityNotFoundException;
import com.example.erp.global.jwt.JwtProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(@Valid SignupReq req) {
        if(memberRepo.existsByLoginId(req.getLoginId())){
            throw new BusinessException("이미 사용중인 아이디입니다.", HttpStatus.CONFLICT);
        }

        Member member = Member.builder()
                .loginId(req.getLoginId())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .role(Role.USER)
                .build();

        memberRepo.save(member);

    }

    //로그인
    @Transactional(readOnly = true)
    public LoginRes login(@Valid LoginReq req) {
        Member member = memberRepo.findByLoginId(req.getLoginId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())){
            throw new BusinessException("비밀번호가 올바르지 않습니다.",HttpStatus.UNAUTHORIZED);
        }

        if(member.getStatus() == MemberStatus.PENDING){
            throw new BusinessException("관리자 승인 대기 중입니다.",HttpStatus.FORBIDDEN);
        }

        String token = jwtProvider.generateToken(
                member.getId(),
                member.getRole().name(),
                member.getTenantId()
        );
        return new LoginRes(token,member.getId(),member.getRole().name(),member.getTenantId());
    }

    //승인 대기 중인 회원 목록 조회
    public List<MemberRes> getPendingMembers() {
        return memberRepo.findAllByStatus(MemberStatus.PENDING)
                .stream()
                .map(MemberRes :: from)
                .toList();
    }

    //회원 승인 + tenantId 할당
    public void approveMember(Long memberId, ApproveReq req) {
        Member member = memberRepo.findById(memberId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));
        if(member.getStatus() == MemberStatus.ACTIVE){
            throw new BusinessException("이미 승인된 회원입니다.",HttpStatus.CONFLICT);
        }
        member.approve(req.getTenantId());
    }
}

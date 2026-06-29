package com.example.erp.domain.auth.service;

import com.example.erp.domain.auth.dto.*;
import com.example.erp.domain.auth.entity.Member;
import com.example.erp.domain.auth.entity.MemberStatus;
import com.example.erp.domain.auth.entity.Role;
import com.example.erp.domain.auth.repository.MemberRepository;
import com.example.erp.global.exception.BusinessException;
import com.example.erp.global.exception.EntityNotFoundException;
import com.example.erp.global.jwt.JwtProvider;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional
    public void signup(@Valid SignupRequest request) {
        if(memberRepository.existsByLoginId(request.loginId())){
            throw new BusinessException("이미 사용중인 아이디입니다.", HttpStatus.CONFLICT);
        }

        Member member = Member.builder()
                .loginId(request.loginId())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest request) {
        Member member = memberRepository.findByLoginId(request.loginId()).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 아이디입니다."));
        if(!passwordEncoder.matches(request.password(), member.getPassword())){
            throw new BusinessException("비밀번호가 올바르지 않습니다.",HttpStatus.UNAUTHORIZED);
        }

        //PENDING 상태 체크
        if(member.getStatus() == MemberStatus.PENDING){
            throw new BusinessException("관리자 승인 대기중입니다.",HttpStatus.FORBIDDEN);
        }

        String token = jwtProvider.generateToken(
                member.getId(),
                member.getRole().name(),
                member.getTenantId()
        );

        return new LoginResponse(token, member.getId(),member.getRole().name(), member.getTenantId());
    }

    //대기 중인 회원 목록
    @Transactional(readOnly = true)
    public List<MemberResponse> getPendingMembers() {
        return memberRepository.findAllByStatus(MemberStatus.PENDING)
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    //회원 승인
    @Transactional
    public void approveMember(Long memberId, @Valid ApproveRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        if(member.getStatus() == MemberStatus.ACTIVE){
            throw new BusinessException("이미 승인된 회원입니다.",HttpStatus.CONFLICT);
        }

        member.approve(request.tenantId());
    }
}

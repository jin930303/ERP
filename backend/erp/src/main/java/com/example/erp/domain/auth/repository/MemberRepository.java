package com.example.erp.domain.auth.repository;

import com.example.erp.domain.auth.entity.Member;
import com.example.erp.domain.auth.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

    List<Member> findAllByStatus(MemberStatus memberStatus);
}

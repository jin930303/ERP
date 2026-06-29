package com.example.erp.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column
    private Long tenantId;

    @Builder
    public Member(String loginId, String password, String name, Role role){
        this.loginId= loginId;
        this.password = password;
        this.name = name;
        this.role=role;
        this.tenantId = null;
    }

    public void approve(Long tenantId){
        this.status = MemberStatus.ACTIVE;
        this.tenantId = tenantId;
    }
}

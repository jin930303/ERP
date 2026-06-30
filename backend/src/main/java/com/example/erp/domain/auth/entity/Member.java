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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Column
    private Long tenantId;

    @Builder
    public Member(String loginId, String password, String name, Role role){
        this.loginId = loginId;
        this.password= password;
        this.name = name;
        this.role = role;
        this.status = MemberStatus.PENDING;
        this.tenantId = null;
    }

    public void approve(Long tenantId){
        this.status= MemberStatus.ACTIVE;
        this.tenantId = tenantId;
    }
}

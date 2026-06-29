package com.example.erp.domain.finance.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "finance_transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long tenantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Builder
    public FinanceTransaction(Long orderId, Long tenantId, TransactionType type, Integer amount){
        this.orderId=orderId;
        this.tenantId=tenantId;
        this.type=type;
        this.amount=amount;
        this.issuedAt=LocalDateTime.now();
    }

}

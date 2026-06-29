package com.example.erp.domain.order.entity;

import com.example.erp.global.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long tenantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Column(nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(Long memberId, Long tenantId){
        this.memberId = memberId;
        this.tenantId = tenantId;
        this.status=OrderStatus.PENDING;
        this.orderedAt=LocalDateTime.now();
    }

    public void confirm(){
        this.status=OrderStatus.CONFIRMED;
    }

    public void cancel(){
        if(this.status != OrderStatus.CONFIRMED ){
            throw new BusinessException("확정된 주문만 취소할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }
        this.status=OrderStatus.CANCELED;
        this.deleted=true;
    }


}

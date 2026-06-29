package com.example.erp.domain.order.service;

import com.example.erp.domain.finance.repository.FinanceRepository;
import com.example.erp.domain.finance.service.FinanceService;
import com.example.erp.domain.inventory.entity.Product;
import com.example.erp.domain.inventory.entity.Stock;
import com.example.erp.domain.inventory.repository.ProductRepository;
import com.example.erp.domain.inventory.repository.StockRepository;
import com.example.erp.domain.order.dto.OrderItemRequest;
import com.example.erp.domain.order.dto.OrderRequest;
import com.example.erp.domain.order.dto.OrderResponse;
import com.example.erp.domain.order.entity.Order;
import com.example.erp.domain.order.entity.OrderItem;
import com.example.erp.domain.order.repository.OrderRepository;
import com.example.erp.global.exception.EntityNotFoundException;
import com.example.erp.global.jwt.JwtMemberInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final FinanceService financeService;

    //주문 등록 - PENDING -> CONFIRMED 단일 트랜잭션
    @Transactional
    public OrderResponse createOrder(@Valid OrderRequest request, JwtMemberInfo memberInfo) {
        Order order = Order.builder()
                .memberId(memberInfo.memberId())
                .tenantId(memberInfo.tenantId())
                .build();

        for(OrderItemRequest itemRequest : request.items()){
            //상품 조회
            Product product = productRepository.findByIdAndDeletedFalse(
                    itemRequest.productId())
                    .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

            //재고 차감
            Stock stock = stockRepository.findByProductAndTenantIdWithLock(
                    itemRequest.productId(), memberInfo.tenantId())
                    .orElseThrow(() -> new EntityNotFoundException("재고 정보를 찾을 수 없습니다."));

            stock.decrease(itemRequest.quantity());

            //주문 항목 추가
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .unitPrice(product.getPrice())
                    .build();

            order.getOrderItems().add(orderItem);
        }

        //PENDING -> CONFIRMED 상태 전이
        order.confirm();
        orderRepository.save(order);

        //DEBIT 전표 발행
        int totalPrice = order.getOrderItems().stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
        financeService.issueDebit(order.getId(),memberInfo.tenantId(),totalPrice);

        return OrderResponse.from(order);
    }


    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId, JwtMemberInfo memberInfo) {
        Order order = orderRepository.findByIdAndTenantId(orderId,memberInfo.tenantId())
                .orElseThrow(()-> new EntityNotFoundException("주문을 찾을 수 없습니다."));

        int totalPrice = order.getOrderItems().stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

        //재고 복구
        for(OrderItem item : order.getOrderItems()){
            Stock stock = stockRepository.findByProductAndTenantIdWithLock(item.getProduct().getId(),memberInfo.tenantId())
                    .orElseThrow(()-> new EntityNotFoundException("재고 정보를 찾을 수 없습니다."));

            stock.increase(item.getQuantity());
        }
        order.cancel();

        //CREDIT 역전표 발행
        financeService.issueCredit(order.getId(),memberInfo.tenantId(),totalPrice);
    }

    //주문 조회
    public List<OrderResponse> getOrders(JwtMemberInfo memberInfo) {
        return orderRepository.findAllByTenantIdAndDeletedFalse(memberInfo.tenantId())
                .stream()
                .map(OrderResponse::from)
                .toList();
    }
}

package com.prog.mediamanager.service;

import com.prog.mediamanager.entity.Delivery;
import com.prog.mediamanager.entity.Member;
import com.prog.mediamanager.entity.Order;
import com.prog.mediamanager.entity.OrderItem;
import com.prog.mediamanager.entity.item.Item;
import com.prog.mediamanager.repository.ItemRepository;
import com.prog.mediamanager.repository.MemberRepository;
import com.prog.mediamanager.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문 생성
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 회원과 상품 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        // 배송지 생성(등록)
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress()); // 추후 수정

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성 및 저장
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);

        return order.getId();
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // 주문 검색

}

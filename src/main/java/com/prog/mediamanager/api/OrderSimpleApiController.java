package com.prog.mediamanager.api;

import com.prog.mediamanager.entity.Order;
import com.prog.mediamanager.repository.OrderRepository;
import com.prog.mediamanager.repository.OrderSearch;
import com.prog.mediamanager.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Order -> Member
 * Order -> Delivery
 * Order -> OrderItem // xxxToMany
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final MemberService memberService;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());//검색 조건 없으므로 전부 조회하게 됨
        return all;
    }
}

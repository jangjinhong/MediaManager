package com.prog.mediamanager.api;

import com.prog.mediamanager.entity.Address;
import com.prog.mediamanager.entity.Order;
import com.prog.mediamanager.entity.OrderStatus;
import com.prog.mediamanager.repository.OrderRepository;
import com.prog.mediamanager.repository.OrderSearch;
import com.prog.mediamanager.repository.order.simplequery.OrderSimpleQueryDto;
import com.prog.mediamanager.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order -> Member
 * Order -> Delivery
 * Order -> OrderItem // xxxToMany
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());//검색 조건 없으므로 전부 조회하게 됨
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() { // 원래는 list 반환x!!
        List<SimpleOrderDto> collect = orderRepository.findAllByCriteria(new OrderSearch()).stream()
                    .map(o -> new SimpleOrderDto(o))
                    .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<SimpleOrderDto> collect = orderRepository.findAllWithMemberDelivery().stream()
                    .map(o -> new SimpleOrderDto(o))
                    .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    public class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) { //dto는 entity 참조해도 됨
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

}

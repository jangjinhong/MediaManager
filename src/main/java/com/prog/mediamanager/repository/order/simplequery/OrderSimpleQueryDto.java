package com.prog.mediamanager.repository.order.simplequery;

import com.prog.mediamanager.entity.Address;
import com.prog.mediamanager.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate,
                               OrderStatus orderStatus, Address address) { //dto는 entity 참조해도 됨
        this.orderId = orderId;
        this.name = name; //LAZY
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}

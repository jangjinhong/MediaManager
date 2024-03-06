package com.prog.mediamanager.repository;

import com.prog.mediamanager.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    // 검색 기준
    private String memberName;
    private OrderStatus orderStatus;


}

package com.prog.mediamanager.controller;

import com.prog.mediamanager.entity.Member;
import com.prog.mediamanager.entity.Order;
import com.prog.mediamanager.entity.item.Item;
import com.prog.mediamanager.repository.OrderSearch;
import com.prog.mediamanager.service.ItemService;
import com.prog.mediamanager.service.MemberService;
import com.prog.mediamanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "orders/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId, //formsummit방식
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        // 엔티티를 만들어 전체를 보낼필요없이, 필요한 것만 명확히하여 전달(영속성 엔티티)
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String list(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> findOrders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", findOrders);
        return "orders/orderList";
    }

    @PostMapping("/orders/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}

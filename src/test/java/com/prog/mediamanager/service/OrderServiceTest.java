package com.prog.mediamanager.service;

import com.prog.mediamanager.entity.Member;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.prog.mediamanager.entity.Address;
import com.prog.mediamanager.entity.Order;
import com.prog.mediamanager.entity.OrderStatus;
import com.prog.mediamanager.entity.item.Book;
import com.prog.mediamanager.entity.item.Item;
import com.prog.mediamanager.exception.NotEnoughStockException;
import com.prog.mediamanager.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("book1", 32000, 10);

        int orderCnt = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCnt);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시, 주문 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류의 개수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("[주문 가격 = 가격*구매수량] 이다", 32000 * orderCnt, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        Member member = createMember();
        Item item = createBook("book1", 10000, 10);
        int orderCnt = 11;

        orderService.order(member.getId(), item.getId(), orderCnt);

        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("book1", 10000, 10);
        int orderCnt = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCnt);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시, 주문 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소시, 재고는 다시 원복되어야 한다.", 10, item.getStockQuantity());
    }


    private Book createBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("c", "s","z"));
        em.persist(member);
        return member;
    }

}
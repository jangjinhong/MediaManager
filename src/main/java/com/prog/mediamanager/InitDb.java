package com.prog.mediamanager;

import com.prog.mediamanager.entity.*;
import com.prog.mediamanager.entity.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 원활한 API 구현을 위해
 * 조회용 샘플 데이터 만들어주겠다
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("정성찬", "서울", "1", "11111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000);
            Book book2 = createBook("JPA2 BOOK", 20000);
            em.persist(book1);
            em.persist(book2);

            OrderItem o1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem o2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);

            Order order = createDelivery(member, o1, o2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("박원빈", "울산", "2", "22222");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 30000);
            Book book2 = createBook("SPRING2 BOOK", 40000);
            em.persist(book1);
            em.persist(book2);

            OrderItem o1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem o2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);

            Order order = createDelivery(member, o1, o2);
            em.persist(order);
        }

        private static Order createDelivery(Member member, OrderItem o1, OrderItem o2) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, o1, o2);
            return order;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            em.persist(member);
            return member;
        }

        private static Book createBook(String name, int price) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(100);
            return book1;
        }
    }

}

package com.prog.mediamanager.repository;

import com.prog.mediamanager.entity.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    /* ---- search ---- */
//    public List<Order> findAll(OrderSearch orderSearch) {
//        return em.createQuery("select o from Order o where id = :id", Order.class)
//                .setParameter("id", id)
//                .getResultList();
//    }
}

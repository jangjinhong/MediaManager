package com.prog.mediamanager.repository;

import com.prog.mediamanager.entity.Member;
import com.prog.mediamanager.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


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
//        // 데이터 == null? jqpl 1 : jqpl 2 ..
//        return em.createQuery("select o from Order o join o.member m" +
//                            "where o.status = :status " +
//                            "and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setMaxResults(1000)
//                .getResultList();
//    }

    public List<Order> findAllByCriteria(OrderSearch orderSearch) { // 추후 QueryDsl로 변경..
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName()
                            + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        /** 3개의 테이블을 한 번의 쿼리로 조회하기
         * 각 필드에 적용시킨 LAZY를 무시하고 실제 객체에 값을 채워서 가져온다 => 페치 조인
         * 1. 기본적으로 다 LAZY 적용시키기
         * 2. 필요한 것들만 객체 그래프를 묶어 갖고오기
         * 위 두가지를 적용시키면 대부분의 성능 문제를 해결할 수 있다!
         */
        return em.createQuery("select o from Order o " +
                        "join fetch o.member m " +
                        "join fetch o.delivery d", Order.class)
                .getResultList();
    }

}

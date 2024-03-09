package com.prog.mediamanager.repository;

import com.prog.mediamanager.entity.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor //final field만 cont- 생성
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) // register a new item
            em.persist(item);
        else
            em.merge(item); // item 수정 (추후 수정^^)
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
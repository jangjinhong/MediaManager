package com.prog.mediamanager.service;

import com.prog.mediamanager.entity.item.Item;
import com.prog.mediamanager.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void udpateItem(Long itemId, String name, int price, int stockQuantity) {        // dirty checking ex
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}

package com.example.shoppinglist.services;

import com.example.shoppinglist.entities.Item;
import com.example.shoppinglist.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {
    @Autowired
    ItemsRepository itemsRepository;

    public List<Item> getShoppingList(Integer userId) {
        return itemsRepository.getAllByUserId(userId);
    }

    public Item addItem(String name, Integer count, Integer userId) {
        return itemsRepository.save(new Item(name, count, userId));
    }

    public boolean markAsBought(Integer itemId) {
        return setBought(itemId, true);
    }

    public boolean markAsNotBought(Integer itemId) {
        return setBought(itemId, false);
    }

    public Integer getUserId(Integer itemId) {
        return itemsRepository.getUserIdById(itemId);
    }

    public void delete(Integer itemId) {
        itemsRepository.deleteById(itemId);
    }

    @Transactional
    boolean setBought(Integer itemId, boolean bought) {
        Optional<Item> item = itemsRepository.findById(itemId);
        if (item.isEmpty())
            return false;
        item.get().setBought(bought);
        itemsRepository.save(item.get());
        return true;
    }
}

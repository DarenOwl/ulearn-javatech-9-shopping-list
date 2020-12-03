package com.example.shoppinglist.services;

import com.example.shoppinglist.entities.Item;
import com.example.shoppinglist.repositories.ItemsRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class ItemsServiceTest {

    @Autowired
    ItemsService itemsService;

    @MockBean
    ItemsRepository itemsRepository;

    @Test
    void getShoppingListCallsGetAllByUserId() {
        List<Item> shoppingList = itemsService.getShoppingList(176);
        Mockito.verify(itemsRepository, Mockito.times(1)).getAllByUserId(176);
    }

    @Test
    void getShoppingList_DoesntUpdateItems() {
        List<Item> shoppingList = itemsService.getShoppingList(176);
        Mockito.verify(itemsRepository, Mockito.times(0)).save(ArgumentMatchers.any(Item.class));
    }

    @Test
    void getShoppingList_ReturnsItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("tomatoes",4,176));
        items.add(new Item("cheese",1,176));
        items.add(new Item("parsley",2,176));

        List<Item> listToReturn = new ArrayList<Item>();
        listToReturn.addAll(items);

        Mockito.doReturn(listToReturn)
                .when(itemsRepository)
                .getAllByUserId(176);

        List<Item> shoppingList = itemsService.getShoppingList(176);
        Assert.notNull(shoppingList, "returned list is null");
        assertEquals(items, shoppingList);
    }

    @Test
    void addItemCallsRepoSave() {
        Item item = new Item("pizza", 1, 1);
        itemsService.addItem(item);
        Mockito.verify(itemsRepository, Mockito.times(1)).save(item);
    }
}
package com.example.shoppinglist.controllers;

import com.example.shoppinglist.entities.Item;
import com.example.shoppinglist.models.ItemModel;
import com.example.shoppinglist.services.ItemsService;
import com.example.shoppinglist.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShoppingListController {
    @Autowired
    ItemsService itemsService;
    @Autowired
    UsersService usersService;

    @RequestMapping(path = "/{token}/shoppinglist", method = RequestMethod.POST)
    public ResponseEntity<?> getList(@PathVariable("token") String token) {

        Integer userId = usersService.getUserIdByToken(token);
        if (userId == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(
                itemsService.getShoppingList(userId),
                HttpStatus.ACCEPTED
        );
    }

    @RequestMapping(path = "/{token}/shoppinglist/add", method = RequestMethod.POST)
    public ResponseEntity<?> addItem(@PathVariable("token") String token, @RequestBody ItemModel itemModel) {
        if (itemModel.name == null || itemModel.name.equals(""))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Integer userId = usersService.getUserIdByToken(token);
        if (userId == null)
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

        Item addedItem = itemsService.addItem(itemModel.name, itemModel.count, userId);
        itemModel.id = addedItem.getId();
        itemModel.name = addedItem.getName();
        itemModel.count = addedItem.getCount();
        itemModel.bought = addedItem.getBought();

        return new ResponseEntity<>(
                itemModel,
                HttpStatus.CREATED
        );
    }

    @RequestMapping(path = "/{token}/shoppinglist/markasbought/{itemid}", method = RequestMethod.POST)
    public HttpStatus markAsBought(@PathVariable("token") String token, @PathVariable("itemid") Integer itemId) {
        if (!itemsService.markAsBought(itemId))
            return HttpStatus.BAD_REQUEST;
        else
            return HttpStatus.ACCEPTED;
    }

    @RequestMapping(path = "/{token}/shoppinglist/markasnotbought/{itemid}", method = RequestMethod.POST)
    public HttpStatus markAsNotBought(@PathVariable("token") String token, @PathVariable("itemid") Integer itemId) {
        if (!itemsService.markAsNotBought(itemId))
            return HttpStatus.BAD_REQUEST;
        else
            return HttpStatus.ACCEPTED;
    }

    @RequestMapping(path = "/{token}/shoppinglist/delete/{itemid}", method = RequestMethod.POST) //вопрос про DELETE
    public HttpStatus delete(@PathVariable("token") String token, @PathVariable("itemid") Integer itemId) {
        itemsService.delete(itemId);
        return HttpStatus.OK;
    }
}

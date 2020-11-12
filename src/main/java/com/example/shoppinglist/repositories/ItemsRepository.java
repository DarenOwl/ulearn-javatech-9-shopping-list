package com.example.shoppinglist.repositories;

import com.example.shoppinglist.entities.Item;
import com.example.shoppinglist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
    @Query("select i from Item i where i.userId = :userId")
    List<Item> getAllByUserId(Integer userId);

    @Query("select i.userId from Item i where i.id = :id")
    Integer getUserIdById(Integer id);
}

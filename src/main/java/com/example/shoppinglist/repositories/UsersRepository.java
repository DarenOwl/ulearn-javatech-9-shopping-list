package com.example.shoppinglist.repositories;

import com.example.shoppinglist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u from User u where UPPER(u.name) = UPPER(:name)")
    User findByName(String name);

    @Query("SELECT u from User u where UPPER(u.email) = UPPER(:email)")
    User findByEmail(String email);

    @Query("SELECT u from User u where UPPER(u.token) = UPPER(:token)")
    User findByToken(String token);
}

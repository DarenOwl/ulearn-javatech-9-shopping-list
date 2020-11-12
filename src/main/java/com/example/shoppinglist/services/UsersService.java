package com.example.shoppinglist.services;

import com.example.shoppinglist.entities.User;
import com.example.shoppinglist.repositories.UsersRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public boolean usernameIsAvailable(String name) {
        //вопрос: возможно, лучше определить в репозитории usersRepository.existsWithName(String name)?
        return usersRepository.findByName(name) == null;
    }

    public boolean emailIsAvailable(String email) {
        return usersRepository.findByEmail(email) == null;
    }

    public User addUser(String name, String email, String password) {
        User user = new User(name, email, password);
        return usersRepository.save(user);
    }

    public User login(String nameOrEmail, String password) {

        User user = usersRepository.findByName(nameOrEmail);
        if (user == null)
            user = usersRepository.findByEmail(nameOrEmail);
        if (user == null)
            return null;

        String hash = BCrypt.hashpw(password, user.getSalt());
        if (user.getPasswordHash().equals(hash)) {
            user.setLastLogin(new Date());
            usersRepository.save(user);
            return user;
        }

        return null;
    }

    public Integer getUserIdByToken(String token) {
        User user = usersRepository.findByToken(token);
        if (user == null)
            return null;
        return user.getId();
    }

    public String getTokenById(Integer userId) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isEmpty())
            return null;
        return user.get().getToken();
    }
}

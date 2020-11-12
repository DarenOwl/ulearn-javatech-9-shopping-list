package com.example.shoppinglist.controllers;

import com.example.shoppinglist.entities.User;
import com.example.shoppinglist.models.LoginModel;
import com.example.shoppinglist.models.RegisterModel;
import com.example.shoppinglist.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    @Autowired
    UsersService usersService;

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> doSignUp(@RequestBody RegisterModel model) {
        String message;

        if (model.name == null || model.name.equals(""))
            return new ResponseEntity<>("имя пользователя не задано", HttpStatus.BAD_REQUEST);
        else if (!usersService.usernameIsAvailable(model.name))
            return new ResponseEntity<>("пользователь с таким именем уже существует", HttpStatus.BAD_REQUEST);

        if (model.email == null || model.email.equals(""))
            return new ResponseEntity<>("email не указан", HttpStatus.BAD_REQUEST);
        else if (!model.email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
            return new ResponseEntity<>("неправильный формат email", HttpStatus.BAD_REQUEST);
        else if (!usersService.emailIsAvailable(model.email))
            return new ResponseEntity<>("пользователь с таким email уже существует", HttpStatus.BAD_REQUEST);

        if (model.password == null || model.password.equals(""))
            return new ResponseEntity<>("пароль не задан", HttpStatus.BAD_REQUEST);
        else if (model.password.length() < 6)
            return new ResponseEntity<>("пароль должен быть длиннее 6 символов", HttpStatus.BAD_REQUEST);

        if (!model.password.equals(model.passwordConfirm))
            return new ResponseEntity<>("пароли не совпадают", HttpStatus.BAD_REQUEST);

        User user = usersService.addUser(model.name, model.email, model.password);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(user.getToken(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> doSignIn(@RequestBody LoginModel loginModel) {

        if (loginModel.login == null || loginModel.login.equals(""))
            return new ResponseEntity<>("не указано имя или email", HttpStatus.BAD_REQUEST);

        if (loginModel.password == null || loginModel.password.equals(""))
            return new ResponseEntity<>("не указан пароль", HttpStatus.BAD_REQUEST);

        User user = usersService.login(loginModel.login, loginModel.password);
        if (user == null)
            return new ResponseEntity<>("неверные имя пользователя или пароль", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(user.getToken(), HttpStatus.ACCEPTED);
    }
}

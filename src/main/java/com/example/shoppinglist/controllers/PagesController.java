package com.example.shoppinglist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class PagesController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getMainPage() {
        return "index.html";
    }
}

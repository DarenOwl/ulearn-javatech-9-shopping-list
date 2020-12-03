package com.example.shoppinglist;

import com.example.shoppinglist.controllers.ShoppingListController;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppinglistApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ShoppingListController shoppingListController;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(shoppingListController);
    }

    @Test
    public void loadPageFromDefaultURL() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void loginCorrect() throws Exception {
        mockMvc.perform(post("/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonLoginForm("almond", "almond")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void loginFailed() throws Exception {
        mockMvc.perform(post("/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonLoginForm("someone", "idontneedpassword")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void itemAdded() throws Exception {
        mockMvc.perform(post("/k_FYrelU_kqtvakMC_Nv4DjzAQp1RZml/shoppinglist/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonItemForm("pizza", 2)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void returnJsonModelOfAddedItem() throws Exception {
        mockMvc.perform(post("/k_FYrelU_kqtvakMC_Nv4DjzAQp1RZml/shoppinglist/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonItemForm("pasta", 1)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(StringContains.containsString("\"name\":\"pasta\"")))
                .andExpect(content().string(StringContains.containsString("\"id\":")))
                .andExpect(content().string(StringContains.containsString("\"count\":1")));
    }

    private String jsonLoginForm(String login, String password) {
        return "{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}";
    }

    private String jsonItemForm(String name, Integer count) {
        return "{\"name\":\"" + name + "\",\"count\":" + count + "}";
    }
}

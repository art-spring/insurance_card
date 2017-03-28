package com.example.card.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by caichunyi on 2017/3/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {


    @Autowired
    private MockMvc mvc;
    private MvcResult result;


    @Test
    public void getStaticInfo() throws Exception {

    }

    @Test
    public void search() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/card/search").param("state", "0");
        result = mvc.perform(request).andReturn();


        System.out.println("result" + result.getResponse().getContentAsString());

    }

}
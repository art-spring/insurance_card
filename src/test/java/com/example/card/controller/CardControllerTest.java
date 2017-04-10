package com.example.card.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.card.params.CardSearchParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Created by caichunyi on 2017/3/23.
 */

public class CardControllerTest extends BaseControllerTest {
    @Test
    public void select() throws Exception {
        this.injectSession();
        CardSearchParam param = new CardSearchParam();
        param.setStatus(1);
        String json = JSONObject.toJSONString(param);
        RequestBuilder request = MockMvcRequestBuilders.post("/card/select").session(this.mockHttpSession).contentType(MediaType.APPLICATION_JSON).content(json);
        result = mvc.perform(request).andReturn();



        System.out.println("result:" + result.getResponse().getContentAsString());
    }

    @Test
    public void test1() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post("/card/test");
        result = mvc.perform(request).andReturn();



        System.out.println("result" + result.getResponse().getContentAsString());

    }




    @Test
    public void search() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/card/search").param("state", "0");
        result = mvc.perform(request).andReturn();



        System.out.println("result" + result.getResponse().getContentAsString());

    }

}
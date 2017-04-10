package com.example.card.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.example.card.entity.Admin;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Created by caichunyi on 2017/3/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mvc;

    protected MvcResult result;


    protected MockHttpSession mockHttpSession;

    protected void injectSession() throws Exception {
        Admin admin = new Admin();
        admin.setLoginName("admin");
        admin.setPassword("123456");
        String jsonBody = JSONObject.toJSONString(admin);
        RequestBuilder builder = MockMvcRequestBuilders.post("/admin/login").contentType(MediaType.APPLICATION_JSON).content(jsonBody);
        this.result = this.mvc.perform(builder).andReturn();
        this.mockHttpSession = (MockHttpSession) this.result.getRequest().getSession();
        System.out.println(this.result.getResponse().getContentAsString());
        System.out.println("session_id:" + this.mockHttpSession.getId());

    }

}

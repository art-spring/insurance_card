package com.example.card.controller;

import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

/**
 * Created by caichunyi on 2017/3/29.
 */
public class AdminControllerTest extends BaseControllerTest {
    @Test
    public void logout() throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders.get("/admin/logout");
        this.result = this.mvc.perform(builder).andReturn();
        System.out.println("response:" + new String(this.result.getResponse().getContentAsByteArray()));
        System.out.println("response string:" + this.result.getResponse().getContentAsString());

    }

    @Test
    public void login() throws Exception {

//        //账号密码正确
//        RequestBuilder builder = MockMvcRequestBuilders.post("/admin/login").param("loginName", "admin").param("password", "123456");
//        this.result = this.mvc.perform(builder).andReturn();
//        System.out.println("response string:" + this.result.getResponse().getContentAsString());
//
//        //不传参数
//        builder = MockMvcRequestBuilders.post("/admin/login");
//        this.result = this.mvc.perform(builder).andReturn();
//        System.out.println("response string:" + this.result.getResponse().getContentAsString());
//        //只传账号
//        builder = MockMvcRequestBuilders.post("/admin/login").param("loginName", "admin");
//        this.result = this.mvc.perform(builder).andReturn();
//        System.out.println("response string:" + this.result.getResponse().getContentAsString());
//        //账号错误
//        builder = MockMvcRequestBuilders.post("/admin/login").param("loginName", "aa").param("password", "123456");
//        this.result = this.mvc.perform(builder).andReturn();
//        System.out.println("response string:" + this.result.getResponse().getContentAsString());
//
//        //密码错误
//        builder = MockMvcRequestBuilders.post("/admin/login").param("loginName", "admin").param("password", "1");
//        this.result = this.mvc.perform(builder).andReturn();
//        System.out.println("response:" + new String(this.result.getResponse().getContentAsByteArray()));
//        System.out.println("response string:" + this.result.getResponse().getContentAsString());
        super.injectSession();
    }

    @Test
    public void testSession() throws Exception {

        System.out.println("with out session test");
        this.login();
        this.logout();
        System.out.println("has session test");
        RequestBuilder builder = MockMvcRequestBuilders.post("/admin/login").param("loginName", "admin").param("password", "123456");
        this.result = this.mvc.perform(builder).andReturn();
        System.out.println("response:" + new String(this.result.getResponse().getContentAsByteArray()));
        System.out.println("response string:" + this.result.getResponse().getContentAsString());

        builder = MockMvcRequestBuilders.get("/admin/logout").session((MockHttpSession) this.result.getRequest().getSession());
        this.result = this.mvc.perform(builder).andReturn();
        System.out.println("response:" + new String(this.result.getResponse().getContentAsByteArray()));
        System.out.println("response string:" + this.result.getResponse().getContentAsString());

        builder = MockMvcRequestBuilders.get("/admin/logout").session((MockHttpSession) this.result.getRequest().getSession());
        this.result = this.mvc.perform(builder).andReturn();
        System.out.println("response:" + new String(this.result.getResponse().getContentAsByteArray()));
        System.out.println("response string:" + this.result.getResponse().getContentAsString());


    }

}
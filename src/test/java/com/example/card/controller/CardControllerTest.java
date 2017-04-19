package com.example.card.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.card.enums.CardState;
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
    public void getStaticInfo() throws Exception {
        this.injectSession();
        RequestBuilder requestBuilder  = MockMvcRequestBuilders.get("/card/getStaticInfo").session(this.mockHttpSession);
        result = mvc.perform(requestBuilder).andReturn();
        System.out.println("result:" + result.getResponse().getContentAsString());

    }

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


    }

    private void search(CardSearchParam param) throws Exception {
        String json = JSONObject.toJSONString(param);

        RequestBuilder request = MockMvcRequestBuilders.post("/card/select").session(this.mockHttpSession).contentType(MediaType.APPLICATION_JSON).content(json);
        result = mvc.perform(request).andReturn();

        System.out.println("result" + result.getResponse().getContentAsString());
    }


    @Test
    public void search() throws Exception {
        this.injectSession();
        CardSearchParam param = new CardSearchParam();
        param.setPage(2);
        param.setPageSize(3);
        this.search(param);
        param.setStatus(CardState.UN_USE.getKey());
        this.search(param);
        param.setStatus(CardState.ACTIVED.getKey());
        this.search(param);
        param.setStatus(CardState.USED.getKey());
        this.search(param);
        param.setPage(1);
        param.setStatus(null);
        param.setAgentName("吴");
        this.search(param);
        param =new CardSearchParam();
        param.setType(1);
        this.search(param);
        param.setType(2);
        this.search(param);
        param =new CardSearchParam();
        param.setKeyword("张");
        this.search(param);
        param.setAgentName("张三");
        this.search(param);



    }

}
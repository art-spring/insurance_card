package com.example.card.controller;

import com.example.card.entity.CardStaticInfo;
import com.example.card.result.JSONResult;
import com.example.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @RequestMapping(value = "getStaticInfo", method = RequestMethod.GET)
    public JSONResult<CardStaticInfo> getStaticInfo() {
        JSONResult<CardStaticInfo> result = new JSONResult<>();
        result.setData(this.cardService.getStaticInfo());

        return result;
    }


}

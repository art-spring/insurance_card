package com.example.card.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.card.entity.CardType;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.CardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@RestController
@RequestMapping("/cardType")
public class CardTypeController {


    @Autowired
    private CardTypeService cardTypeService;

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public JSONResult<List<CardType>> getAll() {
        JSONResult<List<CardType>> result = new JSONResult<>();
        result.setData(this.cardTypeService.selectList(new EntityWrapper<>(null)));

        return result;
    }

}

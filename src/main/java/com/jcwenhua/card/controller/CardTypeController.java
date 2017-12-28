package com.jcwenhua.card.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jcwenhua.card.entity.CardType;
import com.jcwenhua.card.result.JSONResult;
import com.jcwenhua.card.service.CardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

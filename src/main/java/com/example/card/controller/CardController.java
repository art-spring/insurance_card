package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Card;
import com.example.card.entity.CardStaticInfo;
import com.example.card.model.CardInfoModel;
import com.example.card.params.CardSearchParam;
import com.example.card.result.JSONResult;
import com.example.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping(value = "getStaticInfo")
    public JSONResult<CardStaticInfo> getStaticInfo() {
        JSONResult<CardStaticInfo> result = new JSONResult<>();
        result.setData(this.cardService.getStaticInfo());

        return result;
    }

    @PostMapping(value = "/select")
    public JSONResult<List<CardInfoModel>> select(@RequestBody CardSearchParam param) {
        JSONResult<List<CardInfoModel>> result = new JSONResult<>();
        List<CardInfoModel> models = this.cardService.search(param);
        result.setData(models);
        return result;
    }

    @PostMapping("/getDetail")
    public JSONResult<Card> getDetail(int id) {
        JSONResult<Card> result = new JSONResult<>();

        result.setData(this.cardService.selectById(id));
        return result;
    }

    @PostMapping("/setAgent")
    public JSONResult<String> setAgent(int[] ids, int agentId) {
        JSONResult<String> result = new JSONResult<>();

        List<Card> cards = new ArrayList<>();
        Card card;
        for (int id : ids) {
            card = new Card();
            card.setId(id);
            card.setAgentId(agentId);
            cards.add(card);
        }
        this.cardService.updateBatchById(cards);

        return result;
    }


}

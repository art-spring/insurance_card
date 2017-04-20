package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Card;
import com.example.card.entity.CardStaticInfo;
import com.example.card.interceptor.Auth;
import com.example.card.model.CardInfoModel;
import com.example.card.params.CardSearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.CardService;
import com.example.card.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/card")
//@Auth
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping(value = "/getStaticInfo")
    public JSONResult<CardStaticInfo> getStaticInfo() {
        JSONResult<CardStaticInfo> result = new JSONResult<>();
        result.setData(this.cardService.getStaticInfo());

        return result;
    }

    @PostMapping(value = "/select")
    public JSONResult<Page<CardInfoModel>> select(@RequestBody CardSearchParam param) {
        JSONResult<Page<CardInfoModel>> result = new JSONResult<>();
        result.setData(this.cardService.search(param));
        return result;
    }


    @PostMapping("/getDetail")
    public JSONResult<Card> getDetail(int id) {
        JSONResult<Card> result = new JSONResult<>();

        result.setData(this.cardService.selectById(id));
        return result;
    }
//
//    @PostMapping("/setAgent")
//    public JSONResult<String> setAgent(int[] ids, int agentId) {
//        JSONResult<String> result = new JSONResult<>();
//
//        List<Card> cards = new ArrayList<>();
//        Card card;
//        for (int id : ids) {
//            card = new Card();
//            card.setId(id);
//            card.setAgentId(agentId);
//            cards.add(card);
//        }
//        this.cardService.updateBatchById(cards);
//
//        return result;
//    }

    @PostMapping(value = "/insert")
    public JSONResult<Card> insert(@RequestBody Card card) {
        JSONResult<Card> result = new JSONResult<>();

        if (StringUtils.isEmpty(card.getCardNo())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        if (StringUtils.isEmpty(card.getPassword())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        boolean addResult = this.cardService.create(card);
        if (!addResult) {
            result.setResultCode(ResultCode.FAILD);
            result.setData(null);
        }
        result.setData(card);
        return result;
    }

    @GetMapping(value = "/delete")
    public JSONResult<Integer> delete(@RequestParam("keys") String ids) {
        JSONResult<Integer> result = new JSONResult<>();
        List<String> cardIds = Arrays.asList(ids.split(","));
        if (!this.cardService.deleteBatchIds(cardIds)) {
            result.setResultCode(ResultCode.FAILD);
        } else {
            result.setData(cardIds.size());
        }
        return result;
    }

    @PostMapping(value = "/update")
    public JSONResult<Card> update(@RequestParam("keys") String keys, @RequestBody Card updateInfo) {
        JSONResult<Card> result = new JSONResult<>();
        String[] ids = keys.split(",");
        List<Card> oldCards = this.cardService.selectBatchIds(Arrays.asList(ids));

        boolean updateCardNo = !StringUtil.isEmpty(updateInfo.getCardNo());
        boolean updateCardPas = !StringUtil.isEmpty(updateInfo.getPassword());
        boolean updateCardType = updateInfo.getType() != null;
        boolean updateAgent = updateInfo.getAgentId() != null;
        if (oldCards != null && oldCards.size() > 0) {
            for (Card card :  oldCards){
                if (updateCardNo)
                    card.setCardNo(updateInfo.getCardNo());
                if (updateCardPas)
                    card.setPassword(updateInfo.getPassword());
                if (updateCardType)
                    card.setType(updateInfo.getType().intValue());
                if (updateAgent)
                    card.setAgentId(updateInfo.getAgentId().intValue());
            }
            this.cardService.insertOrUpdateBatch(oldCards);
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

}

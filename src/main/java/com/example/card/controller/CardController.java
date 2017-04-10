package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Card;
import com.example.card.entity.CardStaticInfo;
import com.example.card.model.CardInfoModel;
import com.example.card.params.CardSearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.CardService;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/card")
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
    public JSONResult<List<CardInfoModel>> select(@RequestBody CardSearchParam param) {
        JSONResult<List<CardInfoModel>> result = new JSONResult<>();
        List<CardInfoModel> models = this.cardService.search(param);
        result.setData(models);
        return result;
    }

    @PostMapping(value = "/test")
    public JSONResult<Page<Card>> test() {
        JSONResult<Page<Card>> result = new JSONResult<>();
        Page<Card> page = new Page<>();
        result.setData(this.cardService.selectPage(page, null));
        result.getData().getTotal();
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
        if (!this.cardService.deleteBatchIds(cardIds)){
            result.setResultCode(ResultCode.FAILD);
        }else{
            result.setData(cardIds.size());
        }
        return result;
    }

    @PostMapping(value = "/update")
    public JSONResult<Card> update(@RequestParam("keys") String id,@RequestBody Card updateInfo) {
        JSONResult<Card> result = new JSONResult<>();
        Card oldCardInfo = this.cardService.selectById(id);
        if (oldCardInfo!=null){
            oldCardInfo.setCardNo(updateInfo.getCardNo());
            oldCardInfo.setPassword(updateInfo.getPassword());
            oldCardInfo.setType(updateInfo.getType());
            if (!oldCardInfo.updateById()){
                result.setResultCode(ResultCode.FAILD);
            }
        }else{
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

}

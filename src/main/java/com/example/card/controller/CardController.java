package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.*;
import com.example.card.model.CardDetailModel4Wechat;
import com.example.card.model.CardInfoModel;
import com.example.card.params.CardSearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.AgentService;
import com.example.card.service.CardService;
import com.example.card.service.CardTypeService;
import com.example.card.service.CustomerService;
import com.example.card.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private AgentService agentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CardTypeService cardTypeService;

    @PostMapping("options")
    public JSONResult<Map<String, List<Map<String, Object>>>> getStatusOptions(@RequestBody String[] columnNames) {
        JSONResult<Map<String, List<Map<String, Object>>>> result = new JSONResult<>();
        if (columnNames == null || columnNames.length == 0) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        Map<String, List<Map<String, Object>>> output = new HashMap<>();
        for (String columnName : columnNames) {
            switch (columnName) {
                case "customer":
                    List<Map<String, Object>> options3 = new ArrayList<>();
                    List<Customer> allCustomer = customerService.selectByMap(null);
                    if (allCustomer != null && allCustomer.size() > 0) {
                        for (Customer customer : allCustomer) {
                            Map<String, Object> option = new HashMap<>();
                            option.put("text", customer.getName());
                            option.put("value", customer.getId().intValue());
                            options3.add(option);
                        }
                    }
                    output.put("customer", options3);
                    break;
                case "agent":
                    List<Map<String, Object>> options = new ArrayList<>();
                    List<Agent> allAgents = agentService.selectByMap(null);
                    if (allAgents != null && allAgents.size() > 0) {
                        for (Agent agent : allAgents) {
                            Map<String, Object> option = new HashMap<>();
                            option.put("text", agent.getName());
                            option.put("value", agent.getId().intValue());
                            options.add(option);
                        }
                    }
                    output.put("agent", options);
                    break;
                case "cardType":
                    List<Map<String, Object>> options2 = new ArrayList<>();
                    List<CardType> allType = cardTypeService.selectByMap(null);
                    if (allType != null && allType.size() > 0) {
                        for (CardType type : allType) {
                            Map<String, Object> option = new HashMap<>();
                            option.put("text", type.getName());
                            option.put("value", type.getId().intValue());
                            options2.add(option);
                        }
                    }
                    output.put("cardType", options2);
                    break;
                default:
                    break;
            }
        }
        result.setData(output);
        return result;
    }


    @PostMapping(value = "/select")
    public JSONResult<Page<CardInfoModel>> select(@RequestBody CardSearchParam param) {
        JSONResult<Page<CardInfoModel>> result = new JSONResult<>();
        if (param.getStatus() != null && param.getStatus().intValue() == -1) {
            param.setStatus(null);
        }
        if (param.getType() != null && param.getType().intValue() == -1) {
            param.setType(null);
        }
        result.setData(this.cardService.search(param));
        return result;
    }

    @PostMapping("/getDetail")
    public JSONResult<Card> getDetail(int id) {
        JSONResult<Card> result = new JSONResult<>();

        result.setData(this.cardService.selectById(id));
        return result;
    }

    @PostMapping(value = "/insert")
    public JSONResult<Card> insert(@RequestBody Card card) {
        JSONResult<Card> result = new JSONResult<>();

        if (StringUtils.isEmpty(card.getCardNo()) || StringUtils.isEmpty(card.getPassword())) {
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

        boolean updateCardStatus = updateInfo.getStatus() != null;
        boolean updateAgent = updateInfo.getAgentId() != null;
        boolean updateCustomer = updateInfo.getCustomerId() != null;

        if (oldCards != null && oldCards.size() > 0) {
            for (Card card : oldCards) {
                if (updateCardStatus)
                    card.setStatus(updateInfo.getStatus());
                if (updateAgent) {
                    card.setAgentId(updateInfo.getAgentId().intValue());
                    card.setGrantTime(new Date());
                }
                if (updateCustomer)
                    card.setCustomerId(updateInfo.getCustomerId().intValue());
            }
            this.cardService.insertOrUpdateBatch(oldCards);
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }


    //微信端 ---- 代理商
    @PostMapping(value = "wechat/agent/select/{openId}")
    public JSONResult<Page<CardInfoModel>> selectAgentManagerCards(@PathVariable("openId") String openId,
                                                                   @RequestBody CardSearchParam param) {
        JSONResult<Page<CardInfoModel>> result = new JSONResult<>();
        if (StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("wx_id", openId);
        List<Agent> tmp = agentService.selectByMap(map);

        if (tmp != null && tmp.size() > 0) {
            Agent agent = agentService.selectByMap(map).get(0);
            param.setAgentId(agent.getId().intValue());
            result.setData(this.cardService.search(param));
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("代理商未绑定");
        }
        return result;
    }

    @PostMapping(value = "wechat/agent/detail/{openId}/{cardId}")
    public JSONResult<CardDetailModel4Wechat> getAgentManagerCardDetail(@PathVariable("openId") String openId,
                                                                        @PathVariable("cardId") String cardId) {
        JSONResult<CardDetailModel4Wechat> result = new JSONResult<>();
        if (StringUtils.isEmpty(cardId) || StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("wx_id", openId);
        List<Agent> tmp = agentService.selectByMap(map);
        if (tmp != null && tmp.size() > 0) {
            CardDetailModel4Wechat detail = cardService.search4Wechat(Integer.parseInt(cardId));
            if (detail == null) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("id错误");
            }
            result.setData(detail);
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("代理商未绑定");
        }
        return result;
    }


    //微信端 ---- 会员
    @PostMapping(value = "wechat/mycard/detail/{openId}/{cardId}")
    public JSONResult<CardDetailModel4Wechat> getMyCardDetail(@PathVariable("openId") String openId,
                                                              @PathVariable("cardId") String cardId) {
        JSONResult<CardDetailModel4Wechat> result = new JSONResult<>();
        if (StringUtils.isEmpty(cardId) || StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        Customer customer = customerService.findCustomerByOpenId(openId);
        if (customer != null) {
            CardDetailModel4Wechat detail = cardService.search4Wechat(Integer.parseInt(cardId));
            if (detail == null) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("卡片ID错误");
            }
            result.setData(detail);
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("会员未绑定");
        }
        return result;
    }

    @PostMapping(value = "wechat/mycard/active/{openId}")
    public JSONResult<Card> activeMyCard(@PathVariable("openId") String openId,
                                         @RequestBody Map<String, String> params) {
        JSONResult<Card> result = new JSONResult<>();
        if (StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        String cardNo = params.get("cardNo");
        String password = params.get("password");
        String id = params.get("id");

        if (StringUtil.isEmpty(openId)
                || StringUtil.isEmpty(cardNo)
                || StringUtil.isEmpty(password)
                || StringUtil.isEmpty(id)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("wx_id", openId);
        List<Customer> tmp = customerService.selectByMap(map);
        if (tmp != null && tmp.size() == 1) {
            Card card = cardService.selectById(id);
            if (card == null) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("id错误");
            } else {
                if (card.getPassword().equals(password)) {
                    card.setActiveTime(new Date());
                    card.setStatus(1);
                    if (!card.updateById())
                        result.setResultCode(ResultCode.FAILD);
                } else {
                    result.setResultCode(ResultCode.FAILD);
                    result.setMessage("密码错误");
                }

            }
            result.setData(card);
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("会员未绑定");
        }
        return result;
    }

    @PostMapping(value = "wechat/customer/add/{openId}")
    public JSONResult<Card> addCustomerCard(@PathVariable("openId") String openId, @RequestBody Map<String, String> parmas) {

//        type: 1,
//                cardNo: '',
//                password: '',

        String type = parmas.get("type");
        String cardNo = parmas.get("cardNo");
        String password = parmas.get("password");

        JSONResult<Card> result = new JSONResult<>();

        if (StringUtil.isEmpty(openId)
                || StringUtil.isEmpty(type)
                || StringUtil.isEmpty(cardNo)
                || StringUtil.isEmpty(password)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("wx_id", openId);
        List<Customer> tmp = customerService.selectByMap(map);
        if (tmp != null && tmp.size() == 1) {
            Card card = cardService.searchByCardNo(cardNo);
            if (card != null) {
                if (card.getCustomerId() != null) {
                    result.setResultCode(ResultCode.FAILD);
                    result.setMessage("卡片已激活");
                } else {
                    if (card.getPassword().equals(password)) {
                        card.setCustomerId(tmp.get(0).getId().intValue());
                        card.setStatus(1);
                        if (!card.updateById()) {
                            result.setResultCode(ResultCode.FAILD);
                            result.setMessage("激活失败");
                        }
                    } else {
                        result.setResultCode(ResultCode.FAILD);
                        result.setMessage("密码错误");
                    }
                }
            } else {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("卡号不存在");
            }
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("会员未绑定");
        }
        return result;
    }

    @PostMapping(value = "wechat/customer/all/{openId}")
    public JSONResult<List<Card>> getCustomerAllCards(@PathVariable("openId") String openId) {
        JSONResult<List<Card>> result = new JSONResult<>();
        if (StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("wx_id", openId);
        List<Customer> tmp = customerService.selectByMap(map);
        if (tmp != null && tmp.size() == 1) {
            map.clear();
            map.put("customer_id", tmp.get(0).getId());
            result.setData(cardService.selectByMap(map));
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("会员未绑定");
        }
        return result;
    }
}

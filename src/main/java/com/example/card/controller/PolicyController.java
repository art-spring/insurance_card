package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Card;
import com.example.card.entity.CardType;
import com.example.card.entity.Customer;
import com.example.card.entity.Policy;
import com.example.card.params.PolicySearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.CardService;
import com.example.card.service.CardTypeService;
import com.example.card.service.CustomerService;
import com.example.card.service.PolicyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardTypeService cardTypeService;

    @PostMapping(value = "/update")
    public JSONResult<Policy> update(@RequestParam("keys") String keys, @RequestBody Policy updateInfo) {
        JSONResult<Policy> result = new JSONResult<>();
        String[] ids = keys.split(",");
        List<Policy> oldPolicies = this.policyService.selectBatchIds(Arrays.asList(ids));

        boolean updateStatus = updateInfo.getExportStatus() != null;

        if (oldPolicies != null && oldPolicies.size() > 0) {
            for (Policy policy : oldPolicies) {
                if (updateStatus)
                    policy.setExportStatus(updateInfo.getExportStatus().intValue());
            }
            this.policyService.insertOrUpdateBatch(oldPolicies);
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping("/select")
    public JSONResult<Page<Policy>> search(@RequestBody PolicySearchParam param) {
        JSONResult<Page<Policy>> result = new JSONResult<>();
        if (param.getExportStatus() != null && param.getExportStatus().intValue() == -1) {
            param.setExportStatus(null);
        }
        result.setData(this.policyService.search(param));
        return result;
    }

    @PostMapping(value = "/insert")
    public JSONResult<Policy> insert(@NotNull @RequestBody Policy policy) {
        JSONResult<Policy> result = new JSONResult<>();

        if (policy.getCardId() == null) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        if (StringUtils.isEmpty(policy.getHolder())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        if (StringUtils.isEmpty(policy.getRecognizee())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        boolean addResult = this.policyService.createByManager(policy);
        if (!addResult) {
            result.setResultCode(ResultCode.FAILD);
            result.setData(null);
        }
        result.setData(policy);
        return result;
    }

    @PostMapping(value = "wechat/check/{openId}/{cardId}")
    public JSONResult<Map<String, String>> wechatCheckInfo(@PathVariable("openId") String openId,
                                                           @PathVariable("cardId") String cardId) {
        JSONResult<Map<String, String>> result = new JSONResult<>();
        Map<String, Object> map = new HashMap<>();
        map.put("wx_id", openId);
        List<Customer> tmp = customerService.selectByMap(map);
        if (tmp != null && tmp.size() == 1) {
            Customer customer = tmp.get(0);
            Card card = cardService.selectById(cardId);
            if (card == null) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("id错误");
                return result;
            }
            if (card.getCustomerId().intValue() != customer.getId().intValue()) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("卡片不匹配");
                return result;
            }
            Map<String, String> output = new HashMap<>();

            CardType cardType = cardTypeService.selectById(card.getType().intValue());

            if (cardType == null) {
                output.put("typeName", "类型未定义");
            } else {
                output.put("typeName", cardType.getName());
            }

            output.put("name", customer.getName());
            output.put("address", customer.getAddress());
            result.setData(output);
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("会员未绑定");
        }
        return result;
    }


    @PostMapping(value = "wechat/input/{openId}/{cardId}")
    public JSONResult<Policy> inputPolicyInfo(@PathVariable("openId") String openId,
                                              @PathVariable("cardId") String cardId,
                                              @RequestBody Policy policy) {
        JSONResult<Policy> result = new JSONResult<>();
        Map<String, Object> map = new HashMap<>();
        map.put("wx_id", openId);
        List<Customer> tmp = customerService.selectByMap(map);
        if (tmp != null && tmp.size() == 1) {
            Customer customer = tmp.get(0);
            Card card = cardService.selectById(cardId);
            if (card == null) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("id错误");
                return result;
            }
            if (card.getCustomerId().intValue() != customer.getId().intValue()) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("卡片不匹配");
                return result;
            }

            if (!policyService.createByManager(policy))
                result.setResultCode(ResultCode.FAILD);

            //设置卡片未已使用
            if (!cardService.useCard(card))
                result.setResultCode(ResultCode.FAILD);
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("会员未绑定");
        }
        return result;
    }
}

package com.jcwenhua.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.jcwenhua.card.entity.Card;
import com.jcwenhua.card.entity.CardType;
import com.jcwenhua.card.entity.Customer;
import com.jcwenhua.card.entity.Policy;
import com.jcwenhua.card.model.PolicyInfo;
import com.jcwenhua.card.params.PolicySearchParam;
import com.jcwenhua.card.result.JSONResult;
import com.jcwenhua.card.result.ResultCode;
import com.jcwenhua.card.service.CardService;
import com.jcwenhua.card.service.CardTypeService;
import com.jcwenhua.card.service.CustomerService;
import com.jcwenhua.card.service.PolicyService;
import com.jcwenhua.card.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @PostMapping(value = "/update")
    public JSONResult<PolicyInfo> update(@RequestParam("keys") String keys, @RequestBody PolicyInfo updateInfo) {
        JSONResult<PolicyInfo> result = new JSONResult<>();
        String[] ids = keys.split(",");
        List<Policy> oldPolicies = this.policyService.selectBatchIds(Arrays.asList(ids));

//        boolean updateStatus = updateInfo.getExportStatus() != null;

        if (oldPolicies != null && oldPolicies.size() > 0) {
            for (Policy policy : oldPolicies) {
                policy.setHolderAddress(updateInfo.getHolderAddress());
                policy.setHolderBirthday(updateInfo.getHolderBirthday());
                policy.setHolderGender(updateInfo.getHolderGender());
                policy.setHolderIdNo(updateInfo.getHolderIdNo());
                policy.setHolderOccupation(updateInfo.getHolderOccupation());
                policy.setHolderPhone(updateInfo.getHolderPhone());
                policy.setHolder(updateInfo.getHolder());
                policy.setHolderRecognizeeRelation(updateInfo.getHolderRecognizeeRelation());

                policy.setRecognizeeIdNo("".equals(updateInfo.getRecognizeeIdNo()) ? null : updateInfo.getRecognizeeIdNo());
                policy.setRecognizee("".equals(updateInfo.getRecognizee()) ? null : updateInfo.getRecognizee());
                policy.setRecognizeePhone("".equals(updateInfo.getRecognizeePhone()) ? null : updateInfo.getRecognizeePhone());

                policy.setPolicyNumber("".equals(updateInfo.getPolicyNumber()) ? null : updateInfo.getPolicyNumber());

                policy.setStartTime("".equals(updateInfo.getStartTime()) ? null : updateInfo.getStartTime());
                policy.setEndTime("".equals(updateInfo.getEndTime()) ? null : updateInfo.getEndTime());
            }
            this.policyService.insertOrUpdateBatch(oldPolicies);
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping("/select")
    public JSONResult<Page<PolicyInfo>> search(@RequestBody PolicySearchParam param) {
        JSONResult<Page<PolicyInfo>> result = new JSONResult<>();
        if (param.getExportStatus() != null && param.getExportStatus().intValue() == -1) {
            param.setExportStatus(null);
        }
        if (param.getCardType() != null && param.getCardType().intValue() == -1) {
            param.setCardType(null);
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
        Customer customer = customerService.findCustomerByOpenId(openId);
        if (customer != null) {
            Card card = cardService.selectById(cardId);
            if (card == null) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("卡片id错误");
                return result;
            }
            if (card.getCustomerId().intValue() != customer.getId().intValue()) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("数据不匹配");
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
            output.put("idNumber", customer.getIdNumber());
            output.put("phone", customer.getPhoneNumber());
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
        Customer customer = customerService.findCustomerByOpenId(openId);
        if (customer != null) {
            Card card = cardService.selectById(cardId);
            if (card == null) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("卡片id错误");
            } else {
                if (card.getCustomerId().intValue() != customer.getId().intValue()) {
                    result.setResultCode(ResultCode.FAILD);
                    result.setMessage("数据不匹配");
                } else {
                    if (card.getStatus().intValue() == 2) {
                        result.setResultCode(ResultCode.FAILD);
                        result.setMessage("卡片已绑定，请尝试刷新界面");
                    } else {
                        //处理生日
                        String idNo = policy.getHolderIdNo();
                        if (!StringUtil.isEmpty(idNo)) {
                            String birthday = idNo.substring(6, 14);
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                                Date birthdate = sdf.parse(birthday);
                                policy.setHolderBirthday(birthdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        //处理被投保人证件号
                        String idNoR = policy.getRecognizeeIdNo();
                        if (!StringUtil.isEmpty(idNoR) && idNoR.length() > 2) {

                            if (idNoR.startsWith("1-")) {
                                //身份证号码
                                policy.setRecognizeeIdNo(idNoR.split("-")[1]);
                            } else {
                                //出生医学证明号码
                                policy.setRecognizeeIdNo("(出生医学证明号码)" + idNoR.split("-")[1]);
                            }
                        } else {
                            policy.setRecognizeeIdNo(null);
                        }

                        //设置录入时间
                        policy.setCreateTime(new Date());

                        if (!policy.insert()) {
                            result.setResultCode(ResultCode.FAILD);
                            result.setMessage("录入失败");
                        } else {
                            //设置卡片已使用
                            if (!cardService.useCard(card)) {
                                result.setResultCode(ResultCode.FAILD);
                                result.setMessage("录入失败");
                                //TODO:删除失败怎么办
                                policy.deleteById();
                            }
                        }
                    }
                }
            }
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("会员未绑定");
        }
        return result;
    }

    @GetMapping("/delete")
    public JSONResult<String> delete(@RequestParam("keys") String keys) {
        JSONResult<String> result = new JSONResult<>();
        String[] ids = keys.split(",");
        if (policyService.deleteBatchIds(Arrays.asList(ids))){
            result.setMessage("删除成功");
        }else{
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("删除失败");
        }
        return result;
    }
}

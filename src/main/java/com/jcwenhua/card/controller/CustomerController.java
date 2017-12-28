package com.jcwenhua.card.controller;

import com.jcwenhua.card.entity.Card;
import com.jcwenhua.card.entity.Customer;
import com.jcwenhua.card.enums.SmsCodeCheckResult;
import com.jcwenhua.card.model.CustomerBaseInfo;
import com.jcwenhua.card.params.CustomerBindWechatParam;
import com.jcwenhua.card.result.JSONResult;
import com.jcwenhua.card.result.ResultCode;
import com.jcwenhua.card.service.CardService;
import com.jcwenhua.card.service.CustomerService;
import com.jcwenhua.card.utils.SmsUtil;
import com.jcwenhua.card.utils.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CardService cardService;

    @PostMapping("wechat/baseInfo/{openId}")
    public JSONResult<CustomerBaseInfo> getBaseInfo(@PathVariable("openId") String openId) {
        JSONResult<CustomerBaseInfo> result = new JSONResult<>();
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Customer> tmp = customerService.selectByMap(map);
        if (tmp != null && tmp.size() == 1) {
            Customer customer = tmp.get(0);
            CustomerBaseInfo baseInfo = new CustomerBaseInfo();
            baseInfo.setName(customer.getName());
            baseInfo.setPhoneNumber(customer.getPhoneNumber());
            baseInfo.setHeadUrl(customer.getHeadUrl());
            map.clear();
            map.put("customer_id", customer.getId().intValue());
            List<Card> cards = cardService.selectByMap(map);
            baseInfo.setMyLatestCards(cards);
            result.setData(baseInfo);
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping("wechat/info/detail/{openId}")
    public JSONResult<Customer> wechatGetDetail(@PathVariable("openId") String openId) {
        JSONResult<Customer> result = new JSONResult<>();
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Customer> tmp = customerService.selectByMap(map);
        if (tmp != null && tmp.size() == 1) {
            result.setData(tmp.get(0));
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping("wechat/info/modify")
    public JSONResult<Customer> wechatModifyInfo(@RequestBody Map<String, String> params) {
        JSONResult<Customer> result = new JSONResult<>();
//        id:_this.state.id,
//                name:_this.state.name,
//                iDNo:_this.state.iDNo,
//                address:_this.state.address,

        String id = params.get("id");
        String name = params.get("name");
        String iDNo = params.get("iDNo");
        String address = params.get("address");

        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(name)
                || StringUtils.isEmpty(iDNo) || StringUtils.isEmpty(address)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        Customer customer = customerService.selectById(id);

        if (customer != null) {
            customer.setName(name);
            customer.setIdNumber(iDNo);
            customer.setAddress(address);
            if (!customer.updateById()) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("修改失败");
            }
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }


    @GetMapping("detail")
    public JSONResult<Customer> detail(@RequestParam("id") int id) {
        JSONResult<Customer> result = new JSONResult<>();
        Customer customer = customerService.selectById(id);
        if (customer == null) {
            result.setResultCode(ResultCode.FAILD);
        }
        result.setData(customer);
        return result;
    }

    @PostMapping(value = "create")
    public JSONResult<Customer> create(@RequestBody Customer customer) {
        JSONResult<Customer> result = new JSONResult<>();
        if (StringUtils.isEmpty(customer.getPhoneNumber())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            result.setMessage("手机号错误");
        }
        customer.setBindTime(new Date());
        if (!customer.insert()) {
            result.setResultCode(ResultCode.FAILD);
        }
        result.setData(customer);
        return result;
    }

    @PostMapping("wechat/bind")
    public JSONResult<Boolean> bindWechat(@RequestBody CustomerBindWechatParam param) {
        JSONResult<Boolean> result = new JSONResult<>();

        if (StringUtils.isEmpty(param.getPhone()) || StringUtils.isEmpty(param.getSmsCode()) || StringUtils.isEmpty(param.getOpenId())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        if (!StringUtil.isPhone(param.getPhone())) {
            result.setCode(ResultCode.FAILD.getKey());
            result.setMessage("手机号码不正确");
            return result;
        }
        //TODO:判断验证码是否正确
        SmsCodeCheckResult checkResult = SmsUtil.checkCode(param.getPhone(), param.getSmsCode(), 10 * 60 * 1000);
        if (checkResult == SmsCodeCheckResult.MATCH) {
            Customer customer = new Customer();
            customer.setWxId(param.getOpenId());
            customer.setPhoneNumber(param.getPhone());
            if (!customerService.bindWechat(customer)) {
                result.setResultCode(ResultCode.FAILD);
            }
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage(checkResult.getValue());
        }

        return result;
    }

    @PostMapping("/update")
    public JSONResult<Boolean> update(@RequestBody Customer customer) {
        JSONResult<Boolean> result = new JSONResult<>();

        if (StringUtils.isEmpty(customer.getIdNumber()) || StringUtils.isEmpty(customer.getPhoneNumber())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        if (!StringUtil.isPhone(customer.getPhoneNumber())) {
            result.setCode(ResultCode.FAILD.getKey());
            result.setMessage("手机号码不正确");
            return result;
        }

        if (!customer.updateById()) {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping(value = "wechat/checkOpenId/{openId}")
    public JSONResult<Boolean> wechatCheckOpenId(@PathVariable("openId") String openId) {
        JSONResult<Boolean> result = new JSONResult<>();
        if (StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Customer> tmp = customerService.selectByMap(map);
        if (tmp != null && tmp.size() > 0) {
            result.setData(true);
        } else {
            result.setData(false);
        }
        return result;
    }
}

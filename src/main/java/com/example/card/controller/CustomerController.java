package com.example.card.controller;

import com.example.card.entity.Customer;
import com.example.card.params.CustomerBindWechatParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.CustomerService;
import com.example.card.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @GetMapping("/detail")
    public JSONResult<Customer> detail(@RequestParam("id") int id) {
        JSONResult<Customer> result = new JSONResult<>();
        Customer customer = customerService.selectById(id);
        if (customer==null){
            result.setResultCode(ResultCode.FAILD);
        }
        result.setData(customer);
        return result;
    }

    @PostMapping(value = "/create")
    public JSONResult<Customer> create(@RequestBody Customer customer) {
        JSONResult<Customer> result = new JSONResult<>();
        if (StringUtils.isEmpty(customer.getPhoneNumber())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            result.setMessage("手机号错误");
        }
        customer.setBindTime(new Date());
        if (!customer.insert()){
            result.setResultCode(ResultCode.FAILD);
        }
        result.setData(customer);
        return result;
    }

    @PostMapping("/bind")
    public JSONResult<Boolean> bindWechat(@RequestBody CustomerBindWechatParam param){
        JSONResult<Boolean> result = new JSONResult<>();

        if (StringUtils.isEmpty(param.getPhone())||StringUtils.isEmpty(param.getSmsCode())||StringUtils.isEmpty(param.getOpenId())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        if (!StringUtil.isPhone(param.getPhone())) {
            result.setCode(ResultCode.FAILD.getKey());
            result.setMessage("手机号码不正确");
            return result;
        }
        //TODO:判断验证码是否正确

        Customer customer = new Customer();
        customer.setWxId(param.getOpenId());
        customer.setId(param.getId());

        if (!customerService.bindWechat(customer)){
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping("/update")
    public JSONResult<Boolean> update(@RequestBody Customer customer){
        JSONResult<Boolean> result = new JSONResult<>();

        if (StringUtils.isEmpty(customer.getIdNumber())||StringUtils.isEmpty(customer.getPhoneNumber())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        if (!StringUtil.isPhone(customer.getPhoneNumber())) {
            result.setCode(ResultCode.FAILD.getKey());
            result.setMessage("手机号码不正确");
            return result;
        }

        if (!customer.updateById()){
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }
}

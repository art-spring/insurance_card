package com.example.card.controller;

import com.example.card.entity.Customer;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/customer")
public class CustomerController {

    @PostMapping(value = "add")
    public JSONResult<String> add(Customer customer) {
        JSONResult<String> result = new JSONResult<>();
        if (StringUtils.isEmpty(customer.getPhoneNumber())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            result.setData("手机号错误");
        }
        customer.insert();
        return result;
    }
}

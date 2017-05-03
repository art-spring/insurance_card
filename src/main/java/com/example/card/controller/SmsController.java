package com.example.card.controller;

import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by racoon on 2017/4/26.
 */
@RestController
@RequestMapping("/smscode")
public class SmsController {
    @Autowired
    private SmsCodeService smsCodeService;

    @PostMapping("/get/{phoneNumber}")
    public JSONResult<Integer> getCode(@PathVariable("phoneNumber") String phoneNubmer) {
        JSONResult<Integer> result = new JSONResult<>();
        Integer code = smsCodeService.getCode(phoneNubmer);
        result.setData(code);
        if (code == null) {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }
}

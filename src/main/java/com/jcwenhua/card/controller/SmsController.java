package com.jcwenhua.card.controller;

import com.jcwenhua.card.result.JSONResult;
import com.jcwenhua.card.result.ResultCode;
import com.jcwenhua.card.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by racoon on 2017/4/26.
 */
@RestController
@CrossOrigin
@RequestMapping("/smscode")
public class SmsController {
    @Autowired
    private SmsCodeService smsCodeService;

    @PostMapping("/get/{phoneNumber}")
    public JSONResult<Integer> getCode(@PathVariable("phoneNumber") String phoneNubmer) {
        JSONResult<Integer> result = new JSONResult<>();
        Integer code = smsCodeService.getCode(phoneNubmer);
        if (code == null) {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }
}

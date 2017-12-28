package com.jcwenhua.card.service.impl;

import com.alibaba.fastjson.JSON;
import com.jcwenhua.card.config.SmsConfig;
import com.jcwenhua.card.enums.SmsCodeCheckResult;
import com.jcwenhua.card.service.SmsCodeService;
import com.jcwenhua.card.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by racoon on 2017/4/25.
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
    @Autowired
    private SmsConfig smsConfig;

    @Override
    public Integer getCode(String phone) {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        String tpl_value = String.format("#code#=%s", code);
        String result = SmsUtil.tplSendSms(smsConfig.getUriTplSend(), smsConfig.getEncoding(), smsConfig.getApiKey(), Long.parseLong(smsConfig.getTplId()), tpl_value, phone);
        if (result != null && !"".equals(result)) {
            try {
                Map<String, Object> res = JSON.parseObject(result, Map.class);
                int resCode = (int) res.get("code");
                if (resCode == 0){
                    SmsUtil.saveCodeToCache(phone,code);
                    return code;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public SmsCodeCheckResult checkCode(String phone, String smsCode) {
        return SmsUtil.checkCode(phone,smsCode,Long.parseLong(smsConfig.getOverTime()));
    }


}

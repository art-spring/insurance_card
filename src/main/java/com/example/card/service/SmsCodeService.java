package com.example.card.service;

import com.example.card.enums.SmsCodeCheckResult;

/**
 * Created by racoon on 2017/4/25.
 */
public interface SmsCodeService {
    /**
     * 获取验证码
     *
     * @param phone
     * @return Integer
     */
    Integer getCode(String phone);

    /**
     * 验证验证码
     *
     * @param phone
     * @param smsCode 验证码
     * @return Boolean
     */
    SmsCodeCheckResult checkCode(String phone, String smsCode);
}

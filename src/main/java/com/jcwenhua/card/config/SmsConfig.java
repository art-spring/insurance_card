package com.jcwenhua.card.config;

/**
 * Created by racoon on 2017/4/26.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
    @Value("sms.uri_get_user_info")
    private String uriGetUserInfo;
    @Value("sms.uri_send")
    private String uriSend;
    @Value("sms.uri_tpl_send")
    private String uriTplSend;
    @Value("sms.uri_send_voice")
    private String uriSendVoice;
    @Value("sms.api_key")
    private String apiKey;
    @Value("sms.tpl_id")
    private String tplId;
    @Value("sms.encoding")
    private String encoding;
    @Value("sms.check_time")
    private String checkTime;
    @Value("sms.resend_time")
    private String resendTime;
    @Value("sms.over_time")
    private String overTime;
    @Value("sms.check_count")
    private String checkCount;
    @Value("sms.count_time")
    private String countTime;

    public String getUriGetUserInfo() {
        return uriGetUserInfo;
    }

    public void setUriGetUserInfo(String uriGetUserInfo) {
        this.uriGetUserInfo = uriGetUserInfo;
    }

    public String getUriSend() {
        return uriSend;
    }

    public void setUriSend(String uriSend) {
        this.uriSend = uriSend;
    }

    public String getUriTplSend() {
        return uriTplSend;
    }

    public void setUriTplSend(String uriTplSend) {
        this.uriTplSend = uriTplSend;
    }

    public String getUriSendVoice() {
        return uriSendVoice;
    }

    public void setUriSendVoice(String uriSendVoice) {
        this.uriSendVoice = uriSendVoice;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getResendTime() {
        return resendTime;
    }

    public void setResendTime(String resendTime) {
        this.resendTime = resendTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(String checkCount) {
        this.checkCount = checkCount;
    }

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }
}

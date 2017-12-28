package com.jcwenhua.card.params;

/**
 * Created by racoon on 2017/4/10.
 */
public class CustomerBindWechatParam {
    private String openId;
    private String phone;
    private String smsCode;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}

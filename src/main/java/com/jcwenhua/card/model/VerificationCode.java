package com.jcwenhua.card.model;

import java.util.Date;

/**
 * Created by racoon on 2017/4/26.
 */
public class VerificationCode {
    private int code;
    private Date date;
    private String phone;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

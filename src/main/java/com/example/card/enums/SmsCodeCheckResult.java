package com.example.card.enums;

/**
 * Created by racoon on 2017/4/26.
 */
public enum SmsCodeCheckResult {

    TIME_OUT(1,"超时"),
    NOT_MATCH(2,"不匹配"),
    REGET(3,"重新获取"),
    MATCH(4,"匹配成功");

    private int key;
    private String value;

    SmsCodeCheckResult(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

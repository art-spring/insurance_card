package com.example.card.enums;

/**
 * Created by caichunyi on 2017/3/16.
 */
public enum CardState {
    UN_USE(0, "未使用"),
    ACTIVED(1, "已激活"),
    USED(2, "已使用");

    private int key;
    private String value;

    CardState(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

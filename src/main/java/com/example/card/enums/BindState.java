package com.example.card.enums;

/**
 * Created by caichunyi on 2017/3/17.
 */
public enum BindState {
//    0：待审批；1：微信未绑定；2：微信绑定；3：微信解绑
    APPLY(0),
    NEED_BIND(1),
    BIND(2),
    UN_BIND(3);
    private int key;

    BindState(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

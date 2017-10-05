package com.example.card.enums;

/**
 * Created by caichunyi on 2017/3/17.
 */
public enum BindState {
//    0：待审批；2：微信绑定；3：微信解绑
    APPLY(0),
    BIND(1),
    UN_BIND(2);
    private int key;

    BindState(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

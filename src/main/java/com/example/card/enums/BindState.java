package com.example.card.enums;

import com.alibaba.druid.sql.visitor.functions.Bin;

/**
 * Created by caichunyi on 2017/3/17.
 */
public enum BindState {
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

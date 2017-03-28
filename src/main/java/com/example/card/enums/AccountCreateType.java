package com.example.card.enums;

/**
 * Created by caichunyi on 2017/3/17.
 */
public enum AccountCreateType {
    USER_APPLY(0),
    MAMANGER_ADD(1);
    private int key;

    AccountCreateType(int key){
        this.key=key;
    }

    public int getKey() {
        return key;
    }
}

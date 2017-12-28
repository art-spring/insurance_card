package com.jcwenhua.card.enums;

/**
 * Created by caichunyi on 2017/3/17.
 */
public enum ApplyType {
    DEFAULT(0);
    private int key;

    ApplyType(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

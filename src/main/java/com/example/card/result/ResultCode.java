package com.example.card.result;

/**
 * Created by caichunyi on 2017/3/13.
 */
public enum ResultCode {
    SUCCESS(1,"返回成功"),
    FAILD(2,"失败"),
    EXCEPTION(3,"异常"),
    EMPTY_FILE(4,"上傳文件未空"),
    SESSION_NOT_EXISTS(5,"session 不存在"),
    CARD_DATA_ERROP(6,"导入的卡片数据有误");

    private int key;
    private  String value;

     ResultCode(int key ,String value){
        this.key = key;
        this.value =value;
    }

    public int getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}

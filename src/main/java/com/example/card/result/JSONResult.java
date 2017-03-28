package com.example.card.result;

/**
 * Created by caichunyi on 2017/3/13.
 */
public class JSONResult<T> {
    private int code;
    private String message;
    private T data;

    public JSONResult() {
        this.code = ResultCode.SUCCESS.getKey();
        this.message = ResultCode.SUCCESS.getValue();
    }

    public JSONResult(int code) {
        this.code = code;
    }

    public JSONResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public void setResultCode(ResultCode code) {
        this.code = code.getKey();
        this.message = code.getValue();
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

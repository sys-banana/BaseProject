package com.example.baseproject.net.base;

public class ResponThrowable extends RuntimeException{

    private String code;
    private String msg;

    public ResponThrowable(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

}

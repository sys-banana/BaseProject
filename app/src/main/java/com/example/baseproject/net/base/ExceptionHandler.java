package com.example.baseproject.net.base;

import com.example.baseproject.util.Loggers;
import com.google.gson.JsonParseException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ExceptionHandler {

    public static ResponThrowable handleException(Throwable e) {
        ResponThrowable ex = null;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponThrowable("XT001", "XT001");
            switch (httpException.code()) {
                case 404:
                    ex.setMessage("");
                    break;
                default:
                    ex.setMessage(e.getMessage());
                    break;
            }
            ex.setCode(String.valueOf(httpException.code()));
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex =  new ResponThrowable("XT002", "E001");
            ex.setMessage("连接超时，请稍后再试");

        } else if (e instanceof JsonParseException) {
            ex = new ResponThrowable("XT003", "E001");
            ex.setMessage("数据解析失败，请稍后再试");
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            ex =  new ResponThrowable("XT004", "E001");
            ex.setMessage("连接失败，请检查您的网络连接");
        } else if (e instanceof ResponThrowable) {
            ex = (ResponThrowable) e;
//            if (((ResponThrowable) e).getCode() == 10001) {
//                try {
//                    Intent intent = new Intent("tokenAbate");
//                    LocalBroadcastManager.getInstance(HttpService.getContext()).sendBroadcast(intent);
//                } catch (Exception e1) {
//                }
//            }
        } else {
            ex =new ResponThrowable("XT005", "其他");
            ex.setMessage(e.getMessage());
        }
        try{
            e.printStackTrace();
            Loggers.e(e.getMessage());
        }catch (Exception e1){}
        return ex;
    }

}

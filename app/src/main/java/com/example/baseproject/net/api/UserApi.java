package com.example.baseproject.net.api;

import com.example.baseproject.model.base.BaseResponse;
import com.example.baseproject.net.base.HttpService;
import com.example.baseproject.net.base.RxLiftSubscriber;
import com.example.baseproject.net.base.RxSchedulers;
import com.example.baseproject.net.service.UserService;

import java.util.HashMap;

import io.reactivex.Observable;

public class UserApi {

    private UserService service;
    private static volatile UserApi api = null;

    public UserApi() {
        HttpService.initialize().timeOut(HttpService.WRITETIMEOUT);
        service = HttpService.initialize().create(UserService.class);
    }

    public static UserApi getInstance() {
        if (api == null) {
            synchronized (UserApi.class) {
                if (api == null) {
                    api = new UserApi();
                }
            }
        }
        return api;
    }

    public Observable<Object> getUserInfo(HashMap<String, String> param) {
        HttpService.initialize().timeOut(HttpService.WRITETIMEOUT);
        return service.getUserInfo(param)
                .compose(RxSchedulers.<BaseResponse<Object>>io_main())
                .lift(RxLiftSubscriber.beanTransformer());
    }
}

package com.example.baseproject.net.service;

import com.example.baseproject.model.base.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface UserService {


    @GET("api/basic/common/public/news_list")
    Observable<BaseResponse<Object>> getUserInfo(@QueryMap HashMap<String, String> map);
}

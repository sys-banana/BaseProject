package com.example.baseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.baseproject.base.BaseActivity;
import com.example.baseproject.net.api.UserApi;
import com.example.baseproject.net.base.BaseSubscriber;
import com.example.baseproject.net.base.ResponThrowable;

import java.util.HashMap;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test).setOnClickListener(v -> {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("page","1");
            UserApi.getInstance().getUserInfo(map).subscribe(new BaseSubscriber<Object>(compositeDisposable) {
                @Override
                public void onNext(Object o) {
                    super.onNext(o);
                }

                @Override
                public void onError(ResponThrowable e) {

                }
            });
        });
    }
}
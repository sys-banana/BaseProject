package com.example.baseproject.net.base;

import com.example.baseproject.BuildConfig;
import com.example.baseproject.net.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService {
    private static volatile HttpService mHttpService;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;

    //定义超时时间
    public static final int WRITETIMEOUT = 60_000;
    public static final int FILETETIMEOUT = 120_000;
    public static final int READTIMEOUT = 60_000;
    public static final int CONNECTTIMEOUT = 60_000;

    public static HttpService initialize() {
        if (mHttpService == null) {
            synchronized (HttpService.class) {
                if (mHttpService == null) {
                    mHttpService = new HttpService();
                }
            }
        }
        return mHttpService;
    }

    public <T> T create(final Class<T> service) {
        if (mRetrofit != null) {
            return mRetrofit.create(service);
        }
        initHttpClient();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return mRetrofit.create(service);

    }

    private void initHttpClient() {
        if (mOkHttpClient != null) {
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.retryOnConnectionFailure(false);//不重试
        builder.connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITETIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READTIMEOUT, TimeUnit.SECONDS);

        builder.addInterceptor(new HeaderInterceptor());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        mOkHttpClient = builder.build();
    }

    public void readTimeoutMillis(int mTime) {
        initHttpClient();
        if (mOkHttpClient.readTimeoutMillis() != mTime) {
            mOkHttpClient.newBuilder().readTimeout(mTime, TimeUnit.SECONDS);
        }
    }

    public void writeTimeout(int mTime) {
        initHttpClient();
        if (mOkHttpClient.writeTimeoutMillis() != mTime) {
            mOkHttpClient.newBuilder().writeTimeout(mTime, TimeUnit.SECONDS);
        }

    }

    public void connectTimeout(int mTime) {
        initHttpClient();
        if (mOkHttpClient.connectTimeoutMillis() != mTime) {
            mOkHttpClient.newBuilder().connectTimeout(mTime, TimeUnit.SECONDS);
        }
    }

    public void timeOut(int mTime) {
        initHttpClient();
        if (mOkHttpClient.connectTimeoutMillis() != mTime) {
            mOkHttpClient.newBuilder()
                    .readTimeout(mTime, TimeUnit.SECONDS)
                    .writeTimeout(mTime, TimeUnit.SECONDS)
                    .connectTimeout(mTime, TimeUnit.SECONDS);
        }
    }
}

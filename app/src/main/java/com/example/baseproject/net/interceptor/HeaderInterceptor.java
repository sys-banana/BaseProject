package com.example.baseproject.net.interceptor;

import android.os.Build;
import android.text.TextUtils;

import com.example.baseproject.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .header("User-Agent", "BVC Agent")
                .addHeader("appVersion", BuildConfig.VERSION_NAME)
                .addHeader("Build", String.valueOf(BuildConfig.VERSION_CODE))
                .addHeader("appChannel", "bvc pay")
                .addHeader("DeviceId", getUUID());
        if (Build.VERSION.SDK_INT > 13) {
            builder.addHeader("Connection", "close");
        }
        Request request1 = builder.build();
        return chain.proceed(request1);
    }

    private String getUUID() {
        String mIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        String mSerial = Build.SERIAL + "66";
        if (!TextUtils.isEmpty(mSerial)) {
            return new UUID(mIDShort.hashCode(), Build.SERIAL.hashCode()).toString().replace("-", "").toUpperCase();
        } else {
            return new UUID(mIDShort.hashCode(), mIDShort.hashCode()).toString().replace("-", "").toUpperCase();
        }
    }
}

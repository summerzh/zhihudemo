package com.it.zhihudemo;

import com.it.zhihudemo.bean.UrlKey;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by gyt on 2016/8/25
 */
public class NetworkManger {

    public static final String MEIZI_SIZE = "10";

    public static MeiziApi getMeiziApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlKey.MEIZI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        MeiziApi meiziApi = retrofit.create(MeiziApi.class);
        return meiziApi;
    }

    public static MeiziApi getWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.weather.com.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(MeiziApi.class);
    }

}

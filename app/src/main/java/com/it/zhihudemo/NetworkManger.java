package com.it.zhihudemo;

import com.it.zhihudemo.listener.OnLoadDataListener;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * created by gyt on 2016/8/25
 */
public class NetworkManger {

    public static void loadData(String url, final OnLoadDataListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailed(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String data = response.body().string();
                listener.onSuccess(data);
            }
        });
    }

}

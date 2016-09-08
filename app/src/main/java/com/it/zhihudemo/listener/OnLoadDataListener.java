package com.it.zhihudemo.listener;

import java.io.IOException;

/**
 * created by gyt on 2016/9/8
 */
public interface OnLoadDataListener {
    void onFailed(IOException e);
    void onSuccess(String response);
}

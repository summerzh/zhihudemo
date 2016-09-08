package com.it.zhihudemo.listener;

import android.view.View;

import com.it.zhihudemo.GirlInfo;
import com.it.zhihudemo.RatioImageView;

/**
 * created by gyt on 2016/9/8
 */
public interface OnMeiziClickedListener {
    void onMeiziClicked(View v, RatioImageView imageView, GirlInfo.ResultsEntity meizi);
}

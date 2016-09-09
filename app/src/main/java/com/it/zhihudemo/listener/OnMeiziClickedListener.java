package com.it.zhihudemo.listener;

import android.view.View;

import com.it.zhihudemo.bean.MeiziInfo;
import com.it.zhihudemo.widget.RatioImageView;

/**
 * created by gyt on 2016/9/8
 */
public interface OnMeiziClickedListener {
    void onMeiziClicked(View v, RatioImageView imageView, MeiziInfo.ResultsEntity meizi);
}

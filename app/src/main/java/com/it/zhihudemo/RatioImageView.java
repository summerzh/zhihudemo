/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.it.zhihudemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RatioImageView extends ImageView {

    private double hwRatio;


    public RatioImageView(Context context) {
        super(context);
    }


    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取当前ImageView分配的宽度(即Item项的宽度)
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthSize != 0 && hwRatio != 0) {
            //根据高宽比，计算出ImagView需要的高度widthSize* hwRatio，并设置其大小
            setMeasuredDimension(widthSize, (int) (widthSize * hwRatio));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);

    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        hwRatio = bm.getHeight() / (double) bm.getWidth();
        bm.recycle();
    }
}

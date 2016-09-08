package com.it.zhihudemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * created by gyt on 2016/9/8
 */
public class MeiziDetailActivity extends AppCompatActivity implements RequestListener<String, GlideDrawable>, PhotoViewAttacher.OnViewTapListener, View.OnLongClickListener {

    private static final String EXTRA_IMAGE_URL = "image_url";
    public static final String TRANSIT_PIC      = "iv_meizi";
    private ImageView mImageView;
    private String mImageUrl;

    public static Intent newInstance(Context context, String url) {
        Intent intent = new Intent(context, MeiziDetailActivity.class);
        intent.putExtra(MeiziDetailActivity.EXTRA_IMAGE_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d("result", "onCreate ");
        initView();
        initEvent();
        setImageViewAttacher();
    }

    private void setImageViewAttacher() {
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(mImageView);
        photoViewAttacher.setOnViewTapListener(this);
        photoViewAttacher.setOnLongClickListener(this);
    }

    //    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        initEvent();
//    }

    private void initEvent() {
        Intent intent = getIntent();
        mImageUrl = intent.getStringExtra(MeiziDetailActivity.EXTRA_IMAGE_URL);
        Glide.with(this)
                .load(mImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(0)
                .listener(this)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.iv_detail);
        setToolBar();

    }

    private void setToolBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // 注意下面两句代码,如果应用的主题是NoActionBar时,调用会报错, 此时就不需要加了

    }


    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        mImageView.setImageDrawable(resource);
        return false;
    }

    @Override
    public void onViewTap(View view, float x, float y) {

    }

    @Override
    public boolean onLongClick(View v) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.ask_saving_picture))
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveImageToLocal();
                        dialog.dismiss();
                    }
                }).show();
        return false;
    }

    private void saveImageToLocal() {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(this)
                    .load(mImageUrl)
                    .asBitmap()
                    .into(-1, -1)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap == null) {
            Toast.makeText(MeiziDetailActivity.this, "下载图片失败", Toast.LENGTH_SHORT).show();
        }

        File dir = new File(Environment.getExternalStorageDirectory(), "Meizi");
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fileName = mImageUrl;
        File file = new File(dir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(MeiziDetailActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MeiziDetailActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
        }

        // 通知图库刷新]
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        sendBroadcast(intent);
    }
}

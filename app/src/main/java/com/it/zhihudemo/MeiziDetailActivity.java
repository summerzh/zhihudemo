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
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.it.zhihudemo.utils.Share;
import com.it.zhihudemo.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * created by gyt on 2016/9/8
 */
public class MeiziDetailActivity extends AppCompatActivity implements RequestListener<String, GlideDrawable>, PhotoViewAttacher.OnViewTapListener, View.OnLongClickListener {

    private static final String EXTRA_IMAGE_URL = "image_url";
    public static final  String TRANSIT_PIC     = "iv_meizi";
    private static final String TAG             = "MeiziDetailActivity";
    @BindView(R.id.iv_detail)
    ImageView mIvDetail;
    @BindView(R.id.iv_meizi_save)
    ImageView mIvMeiziSave;
    @BindView(R.id.iv_meizi_share)
    ImageView mIvMeiziShare;
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
        ButterKnife.bind(this);
        Log.d("result", "onCreate ");
        initView();
        initEvent();
        setImageViewAttacher();
    }

    @OnClick({
            R.id.iv_meizi_save,
            R.id.iv_meizi_share,
    })
    public void optionClick(View v) {
        switch (v.getId()) {
            case R.id.iv_meizi_save:
                saveImageToLocal();
                break;
            case R.id.iv_meizi_share:
                shareImage();
                break;
        }

    }

    private void shareImage() {
        Uri uri = Uri.parse(mImageUrl);
        Share.shareImage(this, uri, getString(R.string.Share_Meizi_to));
    }

    private void setImageViewAttacher() {
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(mIvDetail);
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
                .listener(this)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }


    private void initView() {
        setToolBar();
    }

    private void setToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }


    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        mIvDetail.setImageDrawable(resource);
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

        Observable.just(mImageUrl)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = Glide.with(MeiziDetailActivity.this)
                                    .load(mImageUrl)
                                    .asBitmap()
                                    .into(-1, -1)
                                    .get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return bitmap;
                    }
                })
                .map(new Func1<Bitmap, Uri>() {
                    @Override
                    public Uri call(Bitmap bitmap) {

                        File dir = new File(Environment.getExternalStorageDirectory(), "Meizi");
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        String fileName = mImageUrl.replace("/", "-");
                        File file = new File(dir, fileName);
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            assert bitmap != null;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 通知图库刷新]
                        Uri uri = Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        sendBroadcast(intent);
                        return uri;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.showShort(R.string.picture_save_failed);
                    }

                    @Override
                    public void onNext(Uri uri) {
                        File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                        String msg = String.format(getString(R.string.picture_has_save_to),
                                appDir.getAbsolutePath());
                        ToastUtil.showShort(msg);
                    }
                });


    }
}

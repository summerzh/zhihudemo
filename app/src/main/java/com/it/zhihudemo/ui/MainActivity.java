package com.it.zhihudemo.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.it.zhihudemo.MeiziApi;
import com.it.zhihudemo.MeiziDetailActivity;
import com.it.zhihudemo.NetworkManger;
import com.it.zhihudemo.R;
import com.it.zhihudemo.adapter.MyRecyclerAdapter;
import com.it.zhihudemo.bean.MeiziInfo;
import com.it.zhihudemo.listener.OnMeiziClickedListener;
import com.it.zhihudemo.widget.RatioImageView;
import com.it.zhihudemo.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnMeiziClickedListener {

    private static final String TAG          = "result";
    private static final int    PRELOAD_SIZE = 6;
    private RecyclerView                  mRecyclerView;
    private MyRecyclerAdapter             mMyRecyclerAdapter;
    private List<MeiziInfo.ResultsEntity> mGirlsList;
    private SwipeRefreshLayout            mRefreshLayout;
    private int mPage = 1;
    private StaggeredGridLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeRefreshLayoutStart();
    }

    private void initEvent() {


        mManager = new StaggeredGridLayoutManager(2,
                1);
        mManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mGirlsList = new ArrayList<>();
        mMyRecyclerAdapter = new MyRecyclerAdapter(this, mGirlsList);
        mRecyclerView.setAdapter(mMyRecyclerAdapter);
        mRecyclerView.addOnScrollListener(getOnBottomListener(mManager));

        mRefreshLayout.setOnRefreshListener(this);
        mMyRecyclerAdapter.setOnMeiziClickedListener(this);
    }

    private RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager manager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 判断是否滑倒底部,findLastCompletelyVisibleItemPositions得到的数是两列图片中后面一列图片
                // 完全显示出来的index
                boolean isBottom =
                        manager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
                                mMyRecyclerAdapter.getItemCount() - PRELOAD_SIZE;
                //                Log.d(TAG, manager.findLastCompletelyVisibleItemPositions(new int[2])[1] + "");
                Log.d(TAG, mMyRecyclerAdapter.getItemCount() - PRELOAD_SIZE + "");
                if (!mRefreshLayout.isRefreshing() && isBottom) {
                    SwipeRefreshLayoutStart();
                }
            }

            //            @Override
            //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //                super.onScrollStateChanged(recyclerView, newState);
            //                mManager.invalidateSpanAssignments();
            //            }
        };
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        setSupportActionBar(toolbar);
        mRefreshLayout.setColorSchemeResources(R.color.swipe_color_1, R.color.swipe_color_2, R.color.swipe_color_3);
    }


    private void requestData() {
        Log.d(TAG, "requestData ");
        MeiziApi meiziApi = NetworkManger.getMeiziApi();
        meiziApi.getMeiziData(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MeiziInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError ");
                        SwipeRefreshLayoutStop();
                    }

                    @Override
                    public void onNext(MeiziInfo meiziInfo) {
                        Log.d(TAG, "onNext ");
                        mGirlsList.addAll(meiziInfo.getResults());
                        mMyRecyclerAdapter.notifyDataSetChanged();
                        if (mRefreshLayout.isRefreshing()) {
                            mPage++;
                            SwipeRefreshLayoutStop();
                        }
                    }
                });


    }

    public void SwipeRefreshLayoutStop() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        });


    }

    public void SwipeRefreshLayoutStart() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onMeiziClicked(View v, RatioImageView view, MeiziInfo.ResultsEntity meizi) {


        Intent intent = MeiziDetailActivity.newInstance(this, meizi.getUrl());
        // 转场动画
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, MeiziDetailActivity.TRANSIT_PIC);
        if (Build.VERSION.SDK_INT > 20) {
            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}


package com.it.zhihudemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.it.zhihudemo.widget.SpacesItemDecoration;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG       = "result";
    private static final int PRELOAD_SIZE = 6;
    private RecyclerView                       mRecyclerView;
    private MyRecyclerAdapter                  mMyRecyclerAdapter;
    private List<GirlInfo.ResultsEntity> mGirlsList;
    private  Handler mHandler = new Handler();
    private SwipeRefreshLayout mRefreshLayout;
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
    }

    private RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager manager) {
        return new RecyclerView.OnScrollListener(){
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
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://gank.io/api/data/福利/10/" + mPage)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                SwipeRefreshLayoutStop();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String string = response.body().string();
                Gson gson = new Gson();
                GirlInfo girlInfo = gson.fromJson(string, GirlInfo.class);

                mGirlsList.addAll(girlInfo.getResults());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMyRecyclerAdapter.notifyDataSetChanged();
                        if (mRefreshLayout.isRefreshing()) {
                            SwipeRefreshLayoutStop();
                        }
                    }
                });
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
        mPage++;
        requestData();
    }
}


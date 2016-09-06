package com.it.zhihudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "result";
    private RecyclerView                 mRecyclerView;
    private MyRecyclerAdapter            mMyRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initEvent() {


//        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        final LinearLayoutManager llManager = new LinearLayoutManager(this);
//        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(llManager);
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
//        mRecyclerView.setPadding(0, 0, 0, 0);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
////                llManager.invalidateSpanAssignments();
//            }
//        });

        requestData();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);
    }


    private void requestData() {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://gank.io/api/data/福利/10/1")
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure ");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, "onResponse ");
                String string = response.body().string();
                Gson gson = new Gson();
                GirlInfo girlInfo = gson.fromJson(string, GirlInfo.class);
                final List<GirlInfo.ResultsEntity> results = girlInfo.getResults();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMyRecyclerAdapter = new MyRecyclerAdapter(MainActivity.this, results);
                        mRecyclerView.setAdapter(mMyRecyclerAdapter);
                    }
                });
            }
        });
    }


}


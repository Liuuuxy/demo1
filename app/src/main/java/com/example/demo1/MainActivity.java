package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Parcelable recyclerViewState;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private List<News> mData = new ArrayList<News>();;
    private NormalAdapter mAmAdapter;
    private Context context = this;
    private boolean isRefresh = false;//是否刷新中
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        //设置SwipeRefreshLayout
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        //上面的方法已经废弃
        mSwipeLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);

        //设置下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(this);
    }


    private void initData() {
        String url = "https://api.apiopen.top/getJoke?page=1&count=10&type=video";
        setHttp(url);
    }

    private void setHttp(String url) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String res = response.body().string();
                Log.i("response===========>", res);
                Gson gson = new Gson();
                NewsList newsList = gson.fromJson(res, NewsList.class);
                mData.addAll(newsList.getNews());
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //initView();
                        viewChange();
                    }
                });
            }
        });

    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mAmAdapter = new NormalAdapter(mData, this);
        // 设置RecyclerView的布局管理
        mRecyclerView.setAdapter(mAmAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAmAdapter.setOnItemClickListener(new NormalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, WebView_Test.class);
                intent.putExtra("name", mData.get(position).getVideo());
                startActivity(intent);
            }


        });
        mRecyclerView.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollToBottom() {
                // 加载更多
                doLoadMore();
            }
        });
        //mAmAdapter.notifyItemRangeChanged(0, mData.size());
        //
    }

    private void viewChange(){
        mAmAdapter.notifyDataSetChanged();
    }
    private void doLoadMore() {
        page++;
        //修改adapter的数据
        String url = "https://api.apiopen.top/getJoke?page=" + page + "&count=10&type=video";
        setHttp(url);
    }

    @Override
    public void onRefresh() {
        //检查是否处于刷新状态
        if (!isRefresh) {
            isRefresh = true;
            //page++;
            //模拟加载网络数据，这里设置4秒，正好能看到4色进度条
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    //显示或隐藏刷新进度条
                    mSwipeLayout.setRefreshing(false);
                    //修改adapter的数据
                    String url = "https://api.apiopen.top/getJoke?page=1&count=10&type=video";
                    mData=new ArrayList<News>();
                    setHttp(url);
                    isRefresh = false;
                }
            }, 2000);
        }
    }
}

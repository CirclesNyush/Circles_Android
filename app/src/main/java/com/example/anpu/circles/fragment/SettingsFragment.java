package com.example.anpu.circles.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.anpu.circles.AddCircleActivity;
import com.example.anpu.circles.R;
import com.example.anpu.circles.adapter.CirclesAdapter;
import com.example.anpu.circles.model.CircleBean;
import com.example.anpu.circles.model.CircleItemLab;
import com.example.anpu.circles.model.CircleResponseBean;
import com.example.anpu.circles.model.UserResponseStatus;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by anpu on 2018/3/5.
 */

public class SettingsFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private CirclesAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private FloatingActionButton addButton;

    private String queryCirlcesUrl = "http://steins.xin:8001/circles/querycircles";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_settings, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.circles_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addButton = rootView.findViewById(R.id.fab_add);

//        List<CircleItem> itemsData = new ArrayList<CircleItem>();
//        CircleItemLab.get(getContext()).addCircleItem(new CircleItem("Blue", "3"));
//        CircleItemLab.get(getContext()).addCircleItem(new CircleItem("Red", "2"));
//        CircleItemLab.get(getContext()).addCircleItem(new CircleItem("Green", "4"));
//        CircleItemLab.get(getContext()).addCircleItem(new CircleItem("Amber","5"));
//        CircleItemLab.get(getContext()).addCircleItem(new CircleItem("Indigo", "1"));

        List<CircleBean> itemsData = CircleItemLab.get(getContext()).getCircleItems();
//        itemsData.add(new CircleItem("Blue", "3"));
//        itemsData.add(new CircleItem("Red", "2"));
//        itemsData.add(new CircleItem("Green", "4"));
//        itemsData.add(new CircleItem("Amber","5"));
//        itemsData.add(new CircleItem("Indigo", "1"));


        mAdapter = new CirclesAdapter(R.layout.circle_item, itemsData);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "you click:" + position, Toast.LENGTH_LONG).show();
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()){
                    case R.id.circle_avatar:
                        Toast.makeText(getContext(), "jump to avatar" , Toast.LENGTH_LONG).show();
                        break;
                    case R.id.circle_title:
                        Toast.makeText(getContext(), "you click title", Toast.LENGTH_LONG).show();
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getEvent(0);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCircleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getEvent(int event_id) {
        final Gson gson = new Gson();

        CircleBean circle = new CircleBean(0);

        String circleString = gson.toJson(circle);
        // post to the server
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), circleString);
        Request request = new Request.Builder()
                .post(body)
                .url(queryCirlcesUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failure to connect to the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ans = response.body().string();
                final CircleResponseBean circleResponseBean = gson.fromJson(ans, CircleResponseBean.class);
                if (circleResponseBean.getStatus() == 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Account is not activated.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    for (CircleResponseBean.DataBean dataBean : circleResponseBean.getData()) {
                        CircleItemLab.get(getActivity()).addCircleItem(new CircleBean(dataBean.getTitle(),
                                dataBean.getContent(), dataBean.getAvatar(),dataBean.getNickname()));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

    }

}

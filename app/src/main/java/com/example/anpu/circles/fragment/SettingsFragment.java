package com.example.anpu.circles.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.anpu.circles.R;
import com.example.anpu.circles.adapter.CirclesAdapter;
import com.example.anpu.circles.model.CircleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anpu on 2018/3/5.
 */

public class SettingsFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private CirclesAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_settings, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.circles_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<CircleItem> itemsData = new ArrayList<CircleItem>();
        itemsData.add(new CircleItem("Blue", "3"));
        itemsData.add(new CircleItem("Red", "2"));
        itemsData.add(new CircleItem("Green", "4"));
        itemsData.add(new CircleItem("Amber","5"));
        itemsData.add(new CircleItem("Indigo", "1"));


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
}

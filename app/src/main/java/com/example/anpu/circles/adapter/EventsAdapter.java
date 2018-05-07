package com.example.anpu.circles.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.anpu.circles.R;
import com.example.anpu.circles.model.EventBean;

import java.util.List;

import static com.example.anpu.circles.fragment.HomeFragment.options;

public class EventsAdapter extends BaseQuickAdapter<EventBean, BaseViewHolder> {


    public EventsAdapter(int layoutResId, @Nullable List<EventBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventBean item) {
        helper.setText(R.id.circle_title, item.getTitle());
        helper.setText(R.id.circle_content, item.getContent());
        Glide.with(mContext).load(item.getAvatar()).apply(options).into((ImageView) helper.getView(R.id.circle_avatar));
        GridView gridView = helper.getView(R.id.gridView);
        gridView.setAdapter(new GridPreViewAdapter(mContext, item.getImgs()));
        if (item.getImgs().size() == 0) {
            helper.getView(R.id.circle_thumbnails).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.circle_thumbnails).setVisibility(View.VISIBLE);
            helper.getView(R.id.circle_thumbnails).setBackgroundColor(255);
        }

    }

    @Nullable
    @Override
    public EventBean getItem(int position) {
        return super.getItem(position);
    }
}

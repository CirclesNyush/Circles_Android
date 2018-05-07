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

public class EventsAdapter extends BaseQuickAdapter<EventBean.EventsBean, BaseViewHolder> {


    public EventsAdapter(int layoutResId, @Nullable List<EventBean.EventsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventBean.EventsBean item) {
        helper.setText(R.id.events_title, item.getTitle());
        helper.setText(R.id.events_location, item.getLocation());
        helper.setText(R.id.events_time, item.getTime());
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.events_img));
    }

    @Nullable
    @Override
    public  EventBean.EventsBean getItem(int position) {
        return super.getItem(position);
    }

    public  void setData(List<EventBean.EventsBean> eventsBeans) {
        this.mData = eventsBeans;
    }

}

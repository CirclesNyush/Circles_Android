package com.example.anpu.circles.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.anpu.circles.R;
import com.example.anpu.circles.model.EventBean;

import java.util.List;

public class EventsAdapter extends BaseQuickAdapter<EventBean.EventsBean, BaseViewHolder> {


    public EventsAdapter(int layoutResId, @Nullable List<EventBean.EventsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventBean.EventsBean item) {
        helper.setText(R.id.event_title, item.getTitle());
        helper.setText(R.id.event_location, item.getLocation());
        helper.setText(R.id.event_time, item.getTime());
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.event_img));

//        GridView gridView = helper.getView(R.id.gridView);
//        List<String> tempImg = new ArrayList<>();
//        tempImg.add(item.getImg());
//        gridView.setAdapter(new GridPreViewAdapter(mContext, tempImg));
//        if (item.getImg() == null) {
//            helper.getView(R.id.event_thumbnails).setVisibility(View.GONE);
//        } else {
//            helper.getView(R.id.event_thumbnails).setVisibility(View.VISIBLE);
//            helper.getView(R.id.event_thumbnails).setBackgroundColor(255);
//        }
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

package com.example.anpu.circles.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.anpu.circles.R;
import com.example.anpu.circles.model.CircleItem;

import java.util.List;

public class CirclesAdapter extends BaseQuickAdapter<CircleItem, BaseViewHolder> {

    public CirclesAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, CircleItem item) {
        helper.setText(R.id.circle_title, item.getTitle());
        helper.setText(R.id.circle_content, item.getContent());
        helper.setImageResource(R.id.circle_avatar, R.mipmap.ic_launcher);
        helper.addOnClickListener(R.id.circle_avatar);
        helper.addOnClickListener(R.id.circle_title);
    }
}

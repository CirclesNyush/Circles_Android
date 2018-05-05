package com.example.anpu.circles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.anpu.circles.R;
import com.example.anpu.circles.utilities.MainConstant;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    public GridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // one more picture at last for adding photos
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        }
        else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent, false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.pic_iv);
        if (position < mList.size()) {
            // show pictures normally before +
            String picUrl = mList.get(position);  // path of pictures
            Glide.with(mContext).load(picUrl).into(iv);
        }
        else {
            iv.setImageResource(R.mipmap.add);  // show + at last
        }
        return convertView;
    }
}

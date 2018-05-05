package com.example.anpu.circles.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.anpu.circles.R;
import com.github.chrisbanes.photoview.PhotoView;


import java.util.List;

public class ViewPagerAdapter extends PagerAdapter{

    private Context context;
    private List<String> imgList;

    public ViewPagerAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    // check if current == the view we want
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = getItemView(R.layout.view_pager_img);
        PhotoView imageView = (PhotoView) itemView.findViewById(R.id.img_iv);
        Glide.with(context).load(imgList.get(position)).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private View getItemView(int layoutId) {
        View itemView = LayoutInflater.from(this.context).inflate(layoutId, null, false);
        return itemView;
    }
}

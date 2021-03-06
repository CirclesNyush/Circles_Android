package com.example.anpu.circles.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.anpu.circles.R;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;

    private int[] images =  {
        R.drawable.one_slide,
        R.drawable.two_slide
    };


    public SlideAdapter (Context context) {
        this.context = context;

    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.linearlayout_slide);
        ImageView imageslide = (ImageView) view.findViewById(R.id.slideimages);
        layoutslide.setBackgroundColor(images[position]);
        imageslide.setImageResource(images[position]);
        container.addView(view);
        return view;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }
}

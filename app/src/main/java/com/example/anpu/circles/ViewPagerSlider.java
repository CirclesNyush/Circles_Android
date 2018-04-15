package com.example.anpu.circles;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerSlider extends ViewPager {

    private float beforeX;

    public ViewPagerSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerSlider(Context context) {

        super(context);
    }

    private boolean isCanScroll = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(isCanScroll){
            return super.dispatchTouchEvent(ev);
        }else  {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    beforeX = ev.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float motionValue = ev.getX() - beforeX;
                    if (motionValue < 0) {
                        return true;
                    }
                    beforeX = ev.getX();

                    break;
                default:
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }

    }



    public boolean isScrollble() {
        return isCanScroll;
    }

    public void setScrollble(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}
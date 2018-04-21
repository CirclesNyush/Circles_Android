package com.example.anpu.circles.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anpu on 2018/3/18.
 */

public class CircleItemLab {

    private static CircleItemLab sCircleItemLab;

    private List<CircleBean> mCircleItems;

    public static CircleItemLab get(Context context) {
        if (sCircleItemLab == null) {
            sCircleItemLab = new CircleItemLab(context);
        }
        return sCircleItemLab;
    }

    private CircleItemLab(Context context) {
        mCircleItems = new ArrayList<>();
    }

    public List<CircleBean> getCircleItems() {
        return mCircleItems;
    }

    public CircleBean getCircleItem(int id) {
        for (CircleBean CircleBean : mCircleItems) {
            if (CircleBean.getId() == id) {
                return CircleBean;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return this.mCircleItems == null || this.mCircleItems.isEmpty();
    }

    public int length() {
        return this.mCircleItems.size();
    }

    public void addCircleItem(CircleBean circleItem) {
        if (! mCircleItems.contains(circleItem)) {
            mCircleItems.add(circleItem);
        }
    }

    public void clear() {
        mCircleItems.clear();
    }
}

package com.example.anpu.circles.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anpu on 2018/3/18.
 */

public class CircleItemLab {

    private static CircleItemLab sCircleItemLab;

    private List<CircleItem> mCircleItems;

    public static CircleItemLab get(Context context) {
        if (sCircleItemLab == null) {
            sCircleItemLab = new CircleItemLab(context);
        }
        return sCircleItemLab;
    }

    private CircleItemLab(Context context) {
        mCircleItems = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CircleItem circleItem = new CircleItem();
            circleItem.setTitle("Circle Item #" + i);
            circleItem.setContent("This Is Circle Item #" + i);
            mCircleItems.add(circleItem);
        }
    }

    public List<CircleItem> getCircleItems() {
        return mCircleItems;
    }

    public CircleItem getCircleItem(int id) {
        for (CircleItem circleItem : mCircleItems) {
            if (circleItem.getId() == id) {
                return circleItem;
            }
        }
        return null;
    }

    public void addCircleItem(CircleItem circleItem) {
        mCircleItems.add(circleItem);
    }
}

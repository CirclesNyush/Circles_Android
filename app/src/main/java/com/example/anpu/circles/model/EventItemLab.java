package com.example.anpu.circles.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class EventItemLab {

    private static EventItemLab sEventItemLab;

    private List<EventBean> mEventItems;

    public static EventItemLab get(Context context) {
        if (sEventItemLab == null) {
            sEventItemLab = new EventItemLab(context);
        }
        return sEventItemLab;
    }

    private EventItemLab(Context context) {
        mEventItems = new ArrayList<>();
    }

    public List<EventBean> getEventItems() {
        return mEventItems;
    }

    public boolean isEmpty() {
        return this.mEventItems == null || this.mEventItems.isEmpty();
    }

    public int length() {
        return this.mEventItems.size();
    }

    public void addEventItem(EventBean eventBean) {
        if (! mEventItems.contains(eventBean)) {
            mEventItems.add(eventBean);
        }
    }

    public void clear() {
        mEventItems.clear();
    }
}

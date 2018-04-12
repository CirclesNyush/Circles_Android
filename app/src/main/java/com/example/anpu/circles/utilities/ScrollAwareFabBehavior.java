package com.example.anpu.circles.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anpu on 2018/3/17.
 * Dirted by wenhe on 2018/4/12.
 */
// TODO: behavior not working
public class ScrollAwareFabBehavior extends FloatingActionButton.Behavior {
//
//    private int toolbarHeight;
//
//    public ScrollAwareFabBehavior(Context context, AttributeSet attrs) {
//        super();
//        this.toolbarHeight = getToolbarHeight(context);
//    }

//    @Override
//    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
//        return super.layoutDependsOn(parent, child, dependency) || (dependency instanceof AppBarLayout);
//    }
//
//    @Override
//    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
//        boolean va = super.onDependentViewChanged(parent, child, dependency);
//
//        if (dependency instanceof AppBarLayout) {
//            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//            int fabBottomMargin = lp.bottomMargin;
//            int distnaceScroll = child.getHeight() + fabBottomMargin;
//            float ratio = (float) dependency.getY() / (float) toolbarHeight;
//            child.setTranslationY(-distnaceScroll * ratio);
//        }
//        return va;
//    }
//
//    public static int getToolbarHeight(Context context) {
//        final TypedArray stypledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
//        int toolbarHeight = (int) stypledAttributes.getDimension(0, 0);
//        stypledAttributes.recycle();
//
//        return toolbarHeight;
//    }


    public ScrollAwareFabBehavior() {
    }

    public ScrollAwareFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    fab.setVisibility(View.INVISIBLE);
                }
            });
        }
        else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }
}
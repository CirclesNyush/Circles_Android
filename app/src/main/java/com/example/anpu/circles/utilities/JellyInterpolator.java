package com.example.anpu.circles.utilities;

import android.view.animation.LinearInterpolator;

/**
 * Created by anpu on 2018/2/5.
 */

public class JellyInterpolator extends LinearInterpolator {
    private float factor;

    public  JellyInterpolator() { this.factor = 0.15f; }

    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }
}

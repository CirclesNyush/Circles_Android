package com.example.anpu.circles.utilities;

import org.junit.Test;

import static org.junit.Assert.*;

public class JellyInterpolatorTest {
    private JellyInterpolator n = new JellyInterpolator();
    @Test
    public void getInterpolation() {
        assertEquals(1.00048828125, n.getInterpolation(1), .00001);
    }
}
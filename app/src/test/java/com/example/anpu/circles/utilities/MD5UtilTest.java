package com.example.anpu.circles.utilities;

import org.junit.Test;

import static org.junit.Assert.*;

public class MD5UtilTest {
    @Test
    public void getMD5Str() {
        assertEquals("3cd24fb0d6963f7d", MD5Util.getMD5Str("abc").toLowerCase());
    }
}
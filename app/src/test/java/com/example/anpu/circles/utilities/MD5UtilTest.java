package com.example.anpu.circles.utilities;

import org.junit.Test;

import static org.junit.Assert.*;

public class MD5UtilTest {
    @Test
    public void getMD5Str() {
        assertEquals("3cd24fb0d6963f7d", MD5Util.getMD5Str("abc").toLowerCase());
        assertEquals("sdf313nrh4h4br4h", MD5Util.getMD5Str("1234567").toLowerCase());
        assertEquals("3c21ed34f34ff445", MD5Util.getMD5Str("poidvc").toLowerCase());
    }
}
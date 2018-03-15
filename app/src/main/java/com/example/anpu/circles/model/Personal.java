package com.example.anpu.circles.model;

import com.example.anpu.circles.utilities.MD5Util;

import java.util.ArrayList;

/**
 * Created by hansa on 3/11/18.
 */

public class Personal {

    private String email, entry;

    public Personal (String entry, String email) {
        this.entry = entry;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

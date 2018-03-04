package com.example.anpu.circles.model;

/**
 * Created by anpu on 2018/2/7.
 */

public class UserStatus {

    private int status;  // 0 failure, 1 success
    private int type;  // 0 not activated, 1 no account
    private int is_new;  // True new

    public UserStatus(int status, int type, int is_new) {
        this.status = status;
        this.type = type;
        this.is_new = is_new;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }
}

package com.example.anpu.circles.model;

public class UserAvatar {

    private String avatar;
    private String email;

    public UserAvatar(String avatar, String email) {
        this.avatar = avatar;
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.anpu.circles.model;

import java.net.URL;

public class CircleItem {
    private String title;
    private String content;
    private String avatar;
    private int id;

    public CircleItem() {
    }

    public CircleItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CircleItem(String title, String content, String avatar) {
        this.title = title;
        this.content = content;
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
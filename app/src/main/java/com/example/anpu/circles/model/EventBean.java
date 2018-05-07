package com.example.anpu.circles.model;

import java.util.List;

public class EventBean {

    /**
     * title : title
     * content : content
     * time : time
     * location : location
     * eventId : 1
     * imgs : [""]
     */

    private String title;
    private String content;
    private String time;
    private String location;
    private int eventId;
    private List<String> imgs;

    private String avatar;


    public EventBean(String title, String content, String time, String location, int eventId, List<String> imgs) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.location = location;
        this.eventId = eventId;
        this.imgs = imgs;
        this.avatar = imgs.get(0);
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

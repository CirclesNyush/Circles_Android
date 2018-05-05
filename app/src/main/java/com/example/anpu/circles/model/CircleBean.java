package com.example.anpu.circles.model;

import java.util.ArrayList;
import java.util.List;

public class CircleBean {
    /**
     * title :
     * content :
     * publisher_id :
     * follower_id : [1,2,3]
     */

    private String title;
    private String content;
    private String publisher_id;
    private List<Integer> follower_id;
    private String avatar;
    private int eventId;
    private String nickname;
    private String location;
    private String time;

    private ArrayList<String> images;

    public CircleBean(String title, String content, String publisher_id, ArrayList<String> imgs, String location, String time) {
        this.title = title;
        this.content = content;
        this.publisher_id = publisher_id;
        this.follower_id = new ArrayList<>();
        this.images = imgs;
        this.location = location;
        this.time = time;
    }

    public CircleBean(int id) {
        this.eventId = id;
    }

    public CircleBean(String title, String content, String avatar, String nickname, int eventId) {
        this.title = title;
        this.content = content;
        this.avatar = avatar;
        this.nickname = nickname;
        this.eventId = eventId;
    }

    public int getId() {
        return eventId;
    }

    public void setId(int id) {
        this.eventId = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
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

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public List<Integer> getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(List<Integer> follower_id) {
        this.follower_id = follower_id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }


    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

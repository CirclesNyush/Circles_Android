package com.example.anpu.circles.model;

import java.util.List;

public class CircleResponseBean {

    /**
     * status :
     * data : [{"title":"","content":"","time":"","avatar":"","nickname":""}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title :
         * content :
         * time :
         * avatar :
         * nickname :
         */

        private String title;
        private String content;
        private String time;
        private String avatar;
        private String nickname;
        private int event_id;
        private List<String> imgs;
        private String location;
        private String email;

        public DataBean(String title, String content, String time, String avatar, String nickname, int event_id, List<String> imgs, String location, String email) {
            this.title = title;
            this.content = content;
            this.time = time;
            this.avatar = avatar;
            this.nickname = nickname;
            this.event_id = event_id;
            this.imgs = imgs;
            this.location = location;
            this.email = email;
        }

        public DataBean(String title, String content, String time, String avatar, String nickname, int event_id) {
            this.title = title;
            this.content = content;
            this.time = time;
            this.avatar = avatar;
            this.nickname = nickname;
            this.event_id = event_id;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getEvent_id() {
            return event_id;
        }

        public void setEvent_id(int event_id) {
            this.event_id = event_id;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}

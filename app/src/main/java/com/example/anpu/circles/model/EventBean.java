package com.example.anpu.circles.model;

import java.util.List;

public class EventBean {


    private List<EventsBean> events;

    public List<EventsBean> getEvents() {
        return events;
    }

    public void setEvents(List<EventsBean> events) {
        this.events = events;
    }

    public static class EventsBean {
        /**
         * title :
         * time :
         * img :
         * link :
         * location :
         */

        private String title;
        private String time;
        private String img;
        private String link;
        private String location;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}

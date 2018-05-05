package com.example.anpu.circles.model;

import java.util.List;

public class NewsBean {

    /**
     * length : 3
     * item : [{"detail":"","img":"","link":"","title":""}]
     */

    private int length;
    private List<ItemBean> item;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * detail :
         * img :
         * link :
         * title :
         */

        private String detail;
        private String img;
        private String link;
        private String title;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

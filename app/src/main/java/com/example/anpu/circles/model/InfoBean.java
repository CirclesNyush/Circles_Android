package com.example.anpu.circles.model;

public class InfoBean {

    /**
     * status : 1
     * data : {"avatar":"user.avatar","nickname":"user.nickname","phone":"user.phone","description":"user.description"}
     */

    private int status;
    private DataBean data;
    private String email;

    public InfoBean (String email, DataBean entry) {
        this.email = email;
        this.data = entry;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail () {return email;}

    public void setEmail (String email) {this.email = email;}

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * avatar : user.avatar
         * nickname : user.nickname
         * phone : user.phone
         * description : user.description
         */

        private String avatar;
        private String nickname;
        private String phone;
        private String description;
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

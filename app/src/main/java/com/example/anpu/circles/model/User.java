package com.example.anpu.circles.model;

/**
 * Created by anpu on 2018/2/1.
 */

public class User {
    private String email;
    private String pwd;
    private int gender;
    private String nickname;

    public User(String email, String pwd, String nickname) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
    }

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
        this.gender = 1;
    }


    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}

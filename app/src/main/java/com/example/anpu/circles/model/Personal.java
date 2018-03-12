package com.example.anpu.circles.model;

import com.example.anpu.circles.utilities.MD5Util;

import java.util.ArrayList;

/**
 * Created by hansa on 3/11/18.
 */

public class Personal {

    private String email;
    private String nickname;
    private String phone;
    private String personal_description;
    private ArrayList<String> tags;

    public Personal () {
        this.email = "add your email";
        this.nickname = "add your username";
        this.phone = "1";
        this.personal_description = "add your personal description";
        this.tags =  new ArrayList<String>();
    }


    public Personal(String email, String cell, String username, String personal_description, ArrayList<String> tags) {
        this.email = email;
        this.nickname = username;
        this.phone = cell;
        this.personal_description = personal_description;
        this.tags = tags;
    }


    public String getEmail() {
        return MD5Util.getMD5(email);
    }

    public void setEmail(String email) {

        String temp = MD5Util.getMD5(email);
        this.email = temp;
    }

    public String getUsername() { return nickname; }

    public void setUsername(String username) { this.nickname = username; }

    public String getCell() {
        return phone;
    }

    public void setCell(String cell) { this.phone = cell; }

    public String getPersonal_description() {
        return personal_description;
    }

    public void setPersonal_description(String personal_description) { this.personal_description = personal_description; }

    public ArrayList<String> getTags () { return tags; }

    public void addTags (ArrayList<String> tags, String addition) {tags.add(addition);}

    //add function to delete tags
}

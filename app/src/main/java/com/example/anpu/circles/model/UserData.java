package com.example.anpu.circles.model;

public class UserData {

    private static String uncypheredEmail;
    private static String email;
    private static String nickname;
    private static String avatar;

    public static String getUncypheredEmail() {
        return uncypheredEmail;
    }

    public static void setUncypheredEmail(String uncypheredEmail) {
        UserData.uncypheredEmail = uncypheredEmail;
    }

    public static String getAvatar() {
        return avatar;
    }

    public static void setAvatar(String avatar) {
        UserData.avatar = avatar;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserData.email = email;
    }


    public static String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        UserData.nickname = nickname;
    }
}

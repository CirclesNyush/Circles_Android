package com.example.anpu.circles.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anpu on 2018/3/18.
 */

public class UserDataLab {

    private static UserDataLab sUserDataLab;

    private List<UserData> mUserDataList;

    private UserDataLab(Context context) {
        mUserDataList = new ArrayList<>();
    }

    public static UserDataLab get(Context context) {
        if (sUserDataLab == null) {
            sUserDataLab = new UserDataLab(context);
        }
        return sUserDataLab;
    }



}

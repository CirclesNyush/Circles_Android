package com.example.anpu.circles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anpu.circles.model.CircleItem;
import com.example.anpu.circles.model.UserData;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by anpu on 2018/3/18.
 */

public class CircleAddActivity extends AppCompatActivity {

    @BindView(R.id.circle_add_title) EditText etTitle;  // et stands for editText
    @BindView(R.id.circle_add_content) EditText etContent;
    @BindView(R.id.circle_add_back) ImageView ivBack;  // iv stands for imageView
    @BindView(R.id.circle_add_post) ImageView ivPost;

    private String title;
    private String content;
    private CircleItem circleItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);
        ButterKnife.bind(this);
    }

    @OnTextChanged(R.id.circle_add_title)
    void titleChanged(CharSequence s, int start, int before, int count) {
        title = s.toString();
    }

    @OnTextChanged(R.id.circle_add_content)
    void contentChanged(CharSequence s, int start, int before, int count) {
        content = s.toString();
    }

    @OnClick(R.id.circle_add_back)
    void backClicked() {
        CircleAddActivity.this.finish();
    }

    @OnClick(R.id.circle_add_post)
    void postClicked() {
        if (title == null || title.equals("")) {
            Toast.makeText(this, "Title is empty!", Toast.LENGTH_LONG).show();
        }
        else if (content == null || title.equals("")) {
            Toast.makeText(this, "Content is empty!", Toast.LENGTH_LONG).show();
        }
        else {
//            postCircleItem();
        }
    }

//    private void postCircleItem() {
//        // generate json
//        Gson gson = new Gson();
//        circleItem = new CircleItem(title, content, UserData.get.getAvatar());
//        String jsonCircleItem = new Gson().toJson(circleItem);
//        // using ok to upload
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(MediaType.parse("applicaion/json; charset=utf-9"), jsonCircleItem);
//        Request request = new Request.Builder()
//                .post(body)
//                .url(URLREQUIRED)
//                .build();
//    }


}

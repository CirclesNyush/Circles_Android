package com.example.anpu.circles;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.anpu.circles.fragment.SettingsFragment;
import com.example.anpu.circles.model.InfoBean;
import com.example.anpu.circles.model.User;
import com.example.anpu.circles.model.UserData;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViewPersonalInfoActivity extends AppCompatActivity {

    private Window window;
    private android.support.v7.widget.Toolbar toolbar;
    private TextView personal_description, cell, nickname, email;
    private CollapsingToolbarLayout coll_tab;

    ImageButton pfp;

    private String fetchAvatar = "http://steins.xin:8001/personal/getinfo";
    private String getInfoUrl = "/personal/getuserinfo";


    private String baseUrl = "http://steins.xin:8001";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_two);
        initView();
        int eventId = getIntent().getIntExtra("eventId", -1);
        getInfo(eventId, this);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private void getInfo(int eventId, final Context theActivity) {
        final Gson gson = new Gson();
        User user = new User(Integer.toString(eventId));
        String userJson = gson.toJson(user);

        Log.d("json", userJson);
        // post to the server
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson);
        Request request = new Request.Builder()
                .post(body)
                .url(baseUrl+getInfoUrl)
                .build();

        //receive from server
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ViewPersonalInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),  "Failure to connect to the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ans = response.body().string();
                final InfoBean infoBean = gson.fromJson(ans, InfoBean.class);
                if (infoBean.getStatus() == 0) {

                    ViewPersonalInfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ViewPersonalInfoActivity.this, "Account is not activated.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    //TODO : add signature
                    ViewPersonalInfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nickname.setText(infoBean.getData().getNickname());
                            cell.setText(infoBean.getData().getPhone());
                            personal_description.setText(infoBean.getData().getDescription());
                            email.setText(infoBean.getData().getEmail());

                            coll_tab.setTitle(infoBean.getData().getNickname());

                            Glide.with(theActivity)
                                    .load(baseUrl + UserData.getAvatar())
                                    .apply(SettingsFragment.options)
                                    .into(pfp);
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        window = ViewPersonalInfoActivity.this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        toolbar = findViewById(R.id.personal_toolbar_two);
        ((AppCompatActivity) ViewPersonalInfoActivity.this).setSupportActionBar( toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 20);

        pfp = findViewById(R.id.personal_pfp);

        nickname = findViewById(R.id.personal_nickname);
        cell = findViewById(R.id.personal_cell);
        email = findViewById(R.id.personal_email);
        personal_description = findViewById(R.id.personal_description_test);
        coll_tab = findViewById(R.id.coll_tab);
    }

}

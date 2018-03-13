package com.example.anpu.circles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.anpu.circles.model.User;
import com.example.anpu.circles.model.UserData;
import com.example.anpu.circles.model.UserResponseStatus;
import com.example.anpu.circles.utilities.MD5Util;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.anpu.circles.utilities.MD5Util.getMD5;

/**
 * Created by anpu on 2018/2/12.
 */

public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences sprefWelcome;
    Intent intent;

    private String checkSessionUrl = "http://steins.xin:8001/auth/checksession";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sprefWelcome = PreferenceManager.getDefaultSharedPreferences(this);


                String email = sprefWelcome.getString("email", "n/a");
                Log.d("test", email);
                checkSession(email);
                WelcomeActivity.this.finish();
    }


    private void checkSession(final String email) {
        final Gson gson = new Gson();
        final User user = new User(getMD5(email));
        String jsonUser = gson.toJson(user);
        // post to the server
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonUser);
        Request request = new Request.Builder()
                .post(body)
                .url(checkSessionUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WelcomeActivity.this, "Failure to connect to the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                UserResponseStatus userResponseStatus = gson.fromJson(response.body().string(), UserResponseStatus.class);
                if (userResponseStatus.getStatus() == 0) {
                    Intent i = new Intent(WelcomeActivity.this, LogInActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(WelcomeActivity.this, HomePageFragmentActivity.class);

                    UserData.setEmail(getMD5(email));
                    UserData.setNickname(userResponseStatus.getNickname());
                    UserData.setAvatar(userResponseStatus.getAvatar());
                    UserData.setUncypheredEmail(email);

                    startActivity(i);
                }
            }
        });
    }
}

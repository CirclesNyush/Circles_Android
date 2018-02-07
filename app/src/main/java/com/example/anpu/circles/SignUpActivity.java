package com.example.anpu.circles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import model.User;
import model.UserStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by anpu on 2018/2/4.
 */

public class SignUpActivity extends AppCompatActivity{

    @BindView(R.id.edit_username_signup) EditText userEditText;
    @BindView(R.id.edit_pwd_signup) EditText pwdEditText;
    @BindView(R.id.signup_button) Button signupButton;

    private String email;
    private String pwd;
    private String urlSignup = "http://steins.xin:8001/auth/signup";


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @OnTextChanged(R.id.edit_username_signup)
    void usernameChanged(CharSequence s, int start, int before, int count) {
        email = s.toString();
    }

    @OnTextChanged(R.id.edit_pwd_signup)
    void pwdChanged(CharSequence s, int start, int before, int count) {
        pwd = s.toString();
    }

    @OnClick(R.id.signup_button)
    void signupClicked() {
        if (email == null || email.equals("")) {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
        }
        else if (pwd == null || pwd.equals("")) {
            Toast.makeText(this,"Password is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            // TO DO
            // check if email is valid
            String pattern = "@nyu.edu";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(email);
            if (! m.find()) {  // invalid email
                Toast.makeText(this, "Username should end with @nyu.edu", Toast.LENGTH_SHORT).show();
            }
            // email verification passed
            else {
                // generate json
                final Gson gson = new Gson();
                User user = new User(email, pwd);
                String jsonUser = gson.toJson(user);
                // post to the server
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonUser);
                Request request = new Request.Builder()
                        .post(body)
                        .url(urlSignup)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignUpActivity.this, "Failure to sign up. Please check your internet connection.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        UserStatus userStatus = gson.fromJson(response.body().string(), UserStatus.class);
                        // success
                        if (userStatus.getStatus() == 1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignUpActivity.this, "An activation email has been sent to your email address. Please click the link in the email to activate your account.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        //failure
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignUpActivity.this, "This email has been used.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}

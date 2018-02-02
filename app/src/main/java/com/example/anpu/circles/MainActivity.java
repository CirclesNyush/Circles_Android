package com.example.anpu.circles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // bind userEditText and pwdEditText
    @BindView(R.id.edit_username) EditText userEditText;
    @BindView(R.id.edit_pwd) EditText pwdEditText;
    // bind buttons
    @BindView(R.id.login_button) Button loginButton;
    @BindView(R.id.signup_button) Button signupButton;

    private String email;
    private String pwd;
    private String urlLogin = "http://steins.xin:8001/auth/login";
    private String urlSignup = "http://steins.xin:8001/auth/signup";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);  // bind userEditText and pwdEditText


        getSupportActionBar().hide();

    }

    @OnTextChanged(R.id.edit_username)
    void usernameChanged(CharSequence s, int start, int before, int count) {
        email = s.toString();
    }

    @OnTextChanged(R.id.edit_pwd)
    void pwdChanged(CharSequence s, int start, int before, int count) {
        pwd = s.toString();
    }

    @OnClick(R.id.login_button)
    void loginClicked() {
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
            if (! m.find()) {
                Toast.makeText(this, "Username should end with @nyu.edu", Toast.LENGTH_SHORT).show();
            }
        }
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
                Gson gson = new Gson();
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
                                Toast.makeText(MainActivity.this, "Failure to sign up. Please check your internet connection.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "An activation email has been sent to your email address. Please click the link in the email to activate your account.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }
    }




    /// three listeners for text change   ---------- IGNORE IT
//    @OnTextChanged(value = R.id.edit_username)
//    void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.d("Test", s.toString());
//    }

//    void afterTextChanged(Editable e) {
//        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//    }
//    void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        Log.d("Test", s.toString());
//    }

//    @OnTextChanged(value = R.id.edit_pwd, callback = OnTextChanged.Callback.TEXT_CHANGED)
//    public void  getPwd(EditText pwd) {
//        Log.d("Test", String.valueOf(pwd.getText()));
//    }


}

package com.example.anpu.circles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    @BindView(R.id.edit_username_login) EditText userEditText;
    @BindView(R.id.edit_pwd_login) EditText pwdEditText;
    // bind buttons
    @BindView(R.id.login_button) Button loginButton;
    // bind textview
    @BindView(R.id.signup_textview) TextView signupTextView;
    @BindView(R.id.forgetpwd_textview) TextView forgetpwdTextView;

    private String email;
    private String pwd;
    private String urlLogin = "http://steins.xin:8001/auth/login";
    private String urlSignup = "http://steins.xin:8001/auth/signup";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);  // bind userEditText and pwdEditText

        // hide the bar at the top
        getSupportActionBar().hide();

    }

    @OnTextChanged(R.id.edit_username_login)
    void usernameChanged(CharSequence s, int start, int before, int count) {
        email = s.toString();
    }

    @OnTextChanged(R.id.edit_pwd_login)
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


    @OnClick(R.id.signup_textview)
    void signupTextClicked() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
//        Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.forgetpwd_textview)
    void forgetpwdTextClicked() {
        Toast.makeText(this, "To do", Toast.LENGTH_SHORT).show();
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

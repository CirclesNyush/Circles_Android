package com.example.anpu.circles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import model.User;

public class MainActivity extends AppCompatActivity {

    // bind userEditText and pwdEditText
    @BindView(R.id.edit_username) EditText userEditText;
    @BindView(R.id.edit_pwd) EditText pwdEditText;
    // bind buttons
    @BindView(R.id.login_button) Button loginButton;
    @BindView(R.id.signup_button) Button signupButton;

    private String username;
    private String pwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);  // bind userEditText and pwdEditText

    }

    @OnTextChanged(R.id.edit_username)
    void usernameChanged(CharSequence s, int start, int before, int count) {
        username = s.toString();
    }

    @OnTextChanged(R.id.edit_pwd)
    void pwdChanged(CharSequence s, int start, int before, int count) {
        pwd = s.toString();
    }

    @OnClick(R.id.login_button)
    void loginClicked() {
        if (username == null || username.equals("")) {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
        }
        else if (pwd == null || pwd.equals("")) {
            Toast.makeText(this,"Password is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            // TO DO
            // check if username is valid
            String pattern = "@nyu.edu";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(username);
            if (! m.find()) {
                Toast.makeText(this, "Username should end with @nyu.edu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.signup_button)
    void signupClicked() {
        if (username == null || username.equals("")) {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
        }
        else if (pwd == null || pwd.equals("")) {
            Toast.makeText(this,"Password is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            // TO DO
            // check if username is valid
            String pattern = "@nyu.edu";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(username);
            if (! m.find()) {  // invalid email
                Toast.makeText(this, "Username should end with @nyu.edu", Toast.LENGTH_SHORT).show();
            }
            // email verification passed
            else {
                // post to the server

//                new Thread() {
//                    public void run() {
//                        Gson gson = new Gson();
//                        User newUser = new User(username, pwd)
//                        String jsonNewUser = gson.toJson(newUser);
//                    }
//                }
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

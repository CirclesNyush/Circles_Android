package com.example.anpu.circles;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

//    @BindView(R.id.signup_layout_email) EditText emailEditText;
//    @BindView(R.id.signup_layout_pwd) EditText pwdEditText;
//    @BindView(R.id.signup_button) Button signupButton;

    @BindView(R.id.main_btn_signup) TextView mBtnLogin;
    @BindView(R.id.layout_progress_signup) View progress;
    @BindView(R.id.input_layout_signup) View mInputLayout;
    @BindView(R.id.signup_layout_email) LinearLayout mEmail;
    @BindView(R.id.signup_layout_pwd) LinearLayout mPwd;

    private float mWidth, mHeight;

    private ActionBar bar;

    private String username;
    private String email;
    private String pwd;
    private String urlSignup = "http://steins.xin:8001/auth/signup";


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
    }

    @OnTextChanged(R.id.edit_username_signup)
    void usernameChanged(CharSequence s, int start, int before, int count) {
        username = s.toString();
    }
    @OnTextChanged(R.id.edit_email_signup)
    void emailChanged(CharSequence s, int start, int before, int count) {
        email = s.toString();
    }

    @OnTextChanged(R.id.edit_pwd_signup)
    void pwdChanged(CharSequence s, int start, int before, int count) {
        pwd = s.toString();
    }

    @OnClick(R.id.main_btn_signup)
    void signupClicked() {
        if (username == null || username.equals("")) {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
        }
        else if (email == null || email.equals("")) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
        }
        else if (pwd == null || pwd.equals("")) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            // Using regex to check if it's nyu email
            checkEmail();
        }
    }

    private void checkEmail() {
        String pattern = "@nyu.edu";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(email);
        if (! m.find()) {
            Toast.makeText(this, "Email should end with @nyu.edu", Toast.LENGTH_LONG).show();
        }
        else {
            mWidth = mBtnLogin.getMeasuredWidth();
            mHeight = mBtnLogin.getMeasuredHeight();

            mEmail.setVisibility(View.INVISIBLE);
            mPwd.setVisibility(View.INVISIBLE);

            inputAnimator(mInputLayout, mWidth, mHeight);
        }
    }

    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 1f, 0.5f);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
//        animator3.setInterpolator(new AccelerateDecelerateInterpolator());
        animator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // check if the account is valid
                validSignup();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator3.start();
    }

    private void validSignup() {
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
                        Toast.makeText(SignUpActivity.this, "Failure to connect to the server", Toast.LENGTH_LONG).show();
                        recovery();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                UserStatus userStatus = gson.fromJson(response.body().string(), UserStatus.class);
                // failure
                if (userStatus.getStatus() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUpActivity.this, "This email has been used.", Toast.LENGTH_LONG).show();
                            recovery();
                        }
                    });
                }
                // success
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUpActivity.this, "An activation email has been sent to your email address. Please click the link in the email to activate your account.", Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                    SignUpActivity.this.finish();
                }
            }
        });
    }

    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mEmail.setVisibility(View.VISIBLE);
        mPwd.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }
}

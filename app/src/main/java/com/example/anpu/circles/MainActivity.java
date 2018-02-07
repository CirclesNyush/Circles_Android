package com.example.anpu.circles;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

//    // bind userEditText and pwdEditText
//    @BindView(R.id.edit_username_login) EditText userEditText;
//    @BindView(R.id.edit_pwd_login) EditText pwdEditText;
//    // bind buttons
//    @BindView(R.id.login_button) Button loginButton;
//    // bind textview
//    @BindView(R.id.signup_textview) TextView signupTextView;
//    @BindView(R.id.forgetpwd_textview) TextView forgetpwdTextView;
    @BindView(R.id.main_btn_login) TextView mBtnLogin;
    @BindView(R.id.layout_progress) View progress;
    @BindView(R.id.input_layout) View mInputLayout;
    @BindView(R.id.login_layout_email) LinearLayout mName;
    @BindView(R.id.login_layout_pwd) LinearLayout mPsw;

    private float mWidth, mHeight;

    private ActionBar bar;


    private String email;
    private String pwd;
    private String urlLogin = "http://steins.xin:8001/auth/login";
    private String urlSignup = "http://steins.xin:8001/auth/signup";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // new
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);  // bind userEditText and pwdEditText

        // hide the bar at the top
//        getSupportActionBar().hide();
        bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
    }

    @OnTextChanged(R.id.edit_email_login)
    void nameChanged(CharSequence s, int start, int before, int count) {
        email = s.toString();
    }

    @OnTextChanged(R.id.edit_pwd_login)
    void pwdChanged(CharSequence s, int start, int before, int count) {
        pwd = s.toString();
    }

    @OnClick(R.id.main_btn_login)
    void loginClicked() {

        mWidth = mBtnLogin.getMeasuredWidth();
        mHeight = mBtnLogin.getMeasuredHeight();

        mName.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);

        inputAnimator(mInputLayout, mWidth, mHeight);
    }

    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new AnimatorUpdateListener() {

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
//                recovery();
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

    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }


//    @OnTextChanged(R.id.edit_username_login)
//    void usernameChanged(CharSequence s, int start, int before, int count) {
//        email = s.toString();
//    }
//
//    @OnTextChanged(R.id.edit_pwd_login)
//    void pwdChanged(CharSequence s, int start, int before, int count) {
//        pwd = s.toString();
//    }
//
//    @OnClick(R.id.login_button)
//    void loginClicked() {
//        if (email == null || email.equals("")) {
//            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
//        }
//        else if (pwd == null || pwd.equals("")) {
//            Toast.makeText(this,"Password is empty", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            // TO DO
//            // check if email is valid
//            String pattern = "@nyu.edu";
//            Pattern r = Pattern.compile(pattern);
//            Matcher m = r.matcher(email);
//            if (! m.find()) {
//                Toast.makeText(this, "Username should end with @nyu.edu", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    @OnClick(R.id.signup_textview)
//    void signupTextClicked() {
//        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
//        startActivity(intent);
////        Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
//    }
//
//
//    @OnClick(R.id.forgetpwd_textview)
//    void forgetpwdTextClicked() {
//        Toast.makeText(this, "To do", Toast.LENGTH_SHORT).show();
//    }




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

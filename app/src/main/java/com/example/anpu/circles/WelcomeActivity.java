package com.example.anpu.circles;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by anpu on 2018/2/12.
 */

public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences sprefWelcome;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sprefWelcome = PreferenceManager.getDefaultSharedPreferences(this);

        // wait 3s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean result = sprefWelcome.getBoolean("login", false);
                Log.d("test", String.valueOf(result));
                System.out.println(result);
                if (result) {
//                    intent = new Intent(WelcomeActivity.this, HomePage1.class);
                    intent = new Intent(WelcomeActivity.this, MainActivityFragment.class);
                }
                else {
                    intent = new Intent(WelcomeActivity.this, MainActivityFragment.class);
//                    intent = new Intent(WelcomeActivity.this, LogInActivity.class);
                }
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 3000);
    }
}

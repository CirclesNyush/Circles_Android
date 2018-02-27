package com.example.anpu.circles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.OnClick;

/**
 * Created by Hansa on 2/18/18.
 */

public class Personal_Information extends AppCompatActivity {

    private ActionBar bar;
    private Button btn_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        //Hide status bar
        bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }

        //go back button
        btn_back = (Button)findViewById(R.id.personal_button_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
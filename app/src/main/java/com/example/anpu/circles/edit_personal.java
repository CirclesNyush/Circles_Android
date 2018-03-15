package com.example.anpu.circles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.anpu.circles.model.Personal;
import com.example.anpu.circles.model.User;
import com.example.anpu.circles.model.UserData;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Hansa on 3/14/18.
 */

public class edit_personal extends AppCompatActivity {


    private Button btn_save;
    private Window window;
    private Toolbar toolbar;
    private String updateinfo = "http://steins.xin:8001/personal/updateinfo";
    private String email,cell,pd, username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_personal);

        //transparent status bar
        window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.edit_personal_toolbar);
        setSupportActionBar(toolbar);

        EditText edit = (EditText) findViewById(R.id.edit_personal_cell);
        cell = edit.getText().toString();
        edit = (EditText) findViewById(R.id.edit_personal_email);
        email = edit.getText().toString();
        edit = (EditText) findViewById(R.id.edit_personal_personal_description);
        pd = edit.getText().toString();
        edit = (EditText) findViewById(R.id.edit_personal_username);
        username = edit.getText().toString();


        //save info to server and return to personal info
        btn_save = (Button)findViewById(R.id.edit_personal_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Gson gson = new Gson();
                Personal person = new Personal();
                String userInfo = gson.toJson(person);
                // post to the server
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfo);
                Request request = new Request.Builder()
                        .post(body)
                        .url(updateinfo)
                        .build();

                onBackPressed();
            }
        });


    }
}

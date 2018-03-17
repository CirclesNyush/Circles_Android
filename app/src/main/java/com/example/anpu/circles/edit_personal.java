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
import android.widget.Toast;

import com.example.anpu.circles.model.InfoBean;
import com.example.anpu.circles.model.Personal;
import com.example.anpu.circles.model.User;
import com.example.anpu.circles.model.UserData;
import com.example.anpu.circles.utilities.MD5Util;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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



        //save info to server and return to personal info
        btn_save = (Button)findViewById(R.id.edit_personal_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.edit_personal_cell);
                cell = edit.getText().toString();
                edit = (EditText) findViewById(R.id.edit_personal_email);
                email = edit.getText().toString();
                edit = (EditText) findViewById(R.id.edit_personal_personal_description);
                pd = edit.getText().toString();
                edit = (EditText) findViewById(R.id.edit_personal_username);
                username = edit.getText().toString();

                ArrayList<String>variables = new ArrayList<String>();
                variables.add(cell);
                variables.add(email);
                variables.add(pd);
                variables.add(username);

                //verify all input fields are filled
                Boolean gate = true;
                for (int n = 0; n < variables.size(); n++) {
                    if (variables.get(n).matches("")) {
                        gate = false;
                    }
                }

                if (gate == true) {

                    InfoBean.DataBean entry = new InfoBean.DataBean();

                    entry.setDescription(pd);
                    entry.setEmail(email);
                    entry.setNickname(username);
                    entry.setPhone(cell);

                    InfoBean entry_package = new InfoBean(MD5Util.getMD5(email), entry);

                    final Gson gson = new Gson();
                    String datajson = gson.toJson(entry_package);
                    // post to the server
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), datajson);
                    Request request = new Request.Builder()
                            .post(body)
                            .url(updateinfo)
                            .build();

                    Toast.makeText(edit_personal.this, "Saved", Toast.LENGTH_LONG).show();
                    onBackPressed();

                }

                else if (gate == false) {
                    Toast.makeText(edit_personal.this, "Fill in all the blanks", Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}

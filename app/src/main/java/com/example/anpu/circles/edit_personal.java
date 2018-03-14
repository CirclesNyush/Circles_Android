package com.example.anpu.circles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Hansa on 3/14/18.
 */

public class edit_personal extends AppCompatActivity {


    private Button btn_save;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_personal);

        //save info and return to personal info
        btn_save = (Button)findViewById(R.id.edit_personal_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}

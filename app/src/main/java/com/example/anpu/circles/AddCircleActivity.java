package com.example.anpu.circles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anpu.circles.model.CircleBean;
import com.example.anpu.circles.model.UserData;
import com.example.anpu.circles.model.UserResponseStatus;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddCircleActivity extends AppCompatActivity {

    @BindView(R.id.add_event_name) EditText eventName;
    @BindView(R.id.add_event_description) EditText eventDetail;
    @BindView(R.id.add_event_submit) Button eventSubmit;

    private String addCircleUrl = "http://steins.xin:8001/circles/postcircles";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        ButterKnife.bind(this);
        eventSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventNameString = String.valueOf(eventName.getText());
                String eventDetailString = String.valueOf(eventDetail.getText());
                if (eventDetailString != null && !eventDetailString.isEmpty()
                        && eventNameString != null && !eventNameString.isEmpty()) {
                    submitEvent(eventNameString, eventDetailString, UserData.getEmail());
                } else {
                    Toast.makeText(AddCircleActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void submitEvent(String title, String content, String publisher) {
        final Gson gson = new Gson();

        CircleBean circle = new CircleBean(title, content, publisher);

        String circleString = gson.toJson(circle);
        // post to the server
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), circleString);
        Request request = new Request.Builder()
                .post(body)
                .url(addCircleUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AddCircleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddCircleActivity.this, "Failure to connect to the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ans = response.body().string();
                final UserResponseStatus userResponseStatus = gson.fromJson(ans, UserResponseStatus.class);
                if (userResponseStatus.getStatus() == 0) {

                    AddCircleActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddCircleActivity.this, "Account is not activated.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    AddCircleActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddCircleActivity.this, "upload", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
            }
        });

    }

}

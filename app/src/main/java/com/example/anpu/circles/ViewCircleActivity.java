package com.example.anpu.circles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anpu.circles.adapter.GridPreViewAdapter;
import com.example.anpu.circles.model.CircleBean;
import com.example.anpu.circles.model.CircleResponseBean;
import com.example.anpu.circles.utilities.MainConstant;
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

public class ViewCircleActivity extends AppCompatActivity {

    private String getEventURL = "http://steins.xin:8001/circles/querycirclesbyid";
    private EditText circleName;
    private EditText description;
    private EditText location;
    private EditText time;
    private EditText email;
    private ImageView back;

    private GridView gridView;
    private GridPreViewAdapter mGridViewAdapter;
    private ArrayList<String> imgs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);
        TextView eventTitle = findViewById(R.id.circle_top);
        eventTitle.setText("");
        ImageView imageView = findViewById(R.id.add_circle_post);
        imageView.setVisibility(View.INVISIBLE);

        circleName = findViewById(R.id.add_circle_name);
        description = findViewById(R.id.add_circle_description);
        location = findViewById(R.id.add_circle_location);
        time = findViewById(R.id.add_circle_time);
        email = findViewById(R.id.add_circle_email);
        back = findViewById(R.id.add_circle_back);

        imgs = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView);
        initGridView();

        setupEdittext(circleName);
        setupEdittext(description);
        setupEdittext(location);
        setupEdittext(time);
        setupEdittext(email);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        int eventId =  getIntent().getIntExtra("eventId", -1);
        getEvent(eventId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initGridView() {
        mGridViewAdapter = new GridPreViewAdapter(ViewCircleActivity.this, imgs);
        gridView.setAdapter(mGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewPulImg(position);
            }
        });
    }

    private void viewPulImg(int position) {
        Intent intent = new Intent(ViewCircleActivity.this, ViewImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgs);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    private void setupEdittext(EditText editText) {
        editText.setFocusableInTouchMode(false);
        editText.setFocusable(false);
    }

    private void getEvent(int eventId) {
        final Gson gson = new Gson();

        CircleBean circle = new CircleBean(eventId);

        String circleString = gson.toJson(circle);
        // post to the server
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), circleString);
        Request request = new Request.Builder()
                .post(body)
                .url(getEventURL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ViewCircleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ViewCircleActivity.this, "Failure to connect to the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ans = response.body().string();
                final CircleResponseBean userResponseStatus = gson.fromJson(ans, CircleResponseBean.class);

                    ViewCircleActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CircleResponseBean.DataBean dataBean = userResponseStatus.getData().get(0);
                            circleName.setText(dataBean.getTitle());
                            description.setText(dataBean.getContent());
                            location.setText(dataBean.getLocation());
                            time.setText(dataBean.getTime());
                            email.setText(dataBean.getEmail());

                            imgs = (ArrayList<String>) dataBean.getImgs();

                            if (imgs.get(0).length() == 0) {
                                imgs.clear();
                            }

                            for (int i = 0 ; i < imgs.size() ; i ++) {
                                Log.d("img", String.valueOf(imgs.get(i).length()));
                                imgs.set(i, "http://steins.xin:8001" + imgs.get(i));
                                Log.d("img", imgs.get(i));
                            }
                            mGridViewAdapter.setImgs(imgs);
                            mGridViewAdapter.notifyDataSetChanged();


                        }
                    });
                }
        });

    }

    private void sendEmail() {

        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:" + email.getText().toString()));
        startActivity(data);
        Log.d("personal", 1+"");
    }

}

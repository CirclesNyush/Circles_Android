package com.example.anpu.circles;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anpu.circles.adapter.GridViewAdapter;
import com.example.anpu.circles.model.CircleBean;
import com.example.anpu.circles.model.UserData;
import com.example.anpu.circles.model.UserResponseStatus;
import com.example.anpu.circles.utilities.GlideV4ImageEngine;
import com.example.anpu.circles.utilities.MainConstant;
import com.example.anpu.circles.utilities.PermissonHelper;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.anpu.circles.utilities.ImageHelper.Bitmap2StrByBase64;

public class AddCircleActivity extends AppCompatActivity {

    @BindView(R.id.add_event_name) EditText eventName;
    @BindView(R.id.add_event_description) EditText eventDetail;
    @BindView(R.id.add_event_location) EditText eventLocation;
    @BindView(R.id.add_event_time) EditText eventTime;
    @BindView(R.id.add_event_post) ImageView eventSubmit;
    @BindView(R.id.add_event_back) ImageView eventBack;
    @BindView(R.id.add_event_email) EditText eventEmail;

    private static final String TAG = "AddCircleActivity";
    private String addCircleUrl = "http://steins.xin:8001/circles/postcircles";
    private Context mContext;
    private GridView gridView;
    private GridViewAdapter mGridViewAdapter;
    private ArrayList<String> mPicList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        ButterKnife.bind(this);

        mContext = this;
        gridView = (GridView) findViewById(R.id.gridView);
        initGridView();
        eventEmail.setVisibility(EditText.GONE);

        eventSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eventNameString = String.valueOf(eventName.getText());
                String eventDetailString = String.valueOf(eventDetail.getText());
                String eventLocationString = String.valueOf(eventLocation.getText());
                String eventTimeString = String.valueOf(eventTime.getText());

                if (eventDetailString != null && !eventDetailString.isEmpty()
                        && eventNameString != null && !eventNameString.isEmpty()) {
                    submitEvent(AddCircleActivity.this, eventNameString, eventDetailString, UserData.getEmail(), eventLocationString, eventTimeString);
                } else {
                    Toast.makeText(AddCircleActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void initGridView() {
        mGridViewAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    // if + is at the last position and there are less than 5 pics
                    // user can add pic
                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        // at most 5 pics
                        viewPulImg(position);
                    }
                    else {
                        // add pic
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                }
                else {
                    viewPulImg(position);
                }
            }
        });
    }

    // view full size pic
    private void viewPulImg(int position) {
        Intent intent = new Intent(mContext, AddImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    private void selectPic(int maxTotal) {
        // asking for permission
        PermissonHelper.askStroage(this);
        PermissonHelper.askCamera(this);
        // use Matisse
        Matisse.from(AddCircleActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(MainConstant.MAX_SELECT_PIC_NUM)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.picker))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Zhihu)
                .imageEngine(new GlideV4ImageEngine())
                .forResult(MainConstant.REQUEST_CODE_MAIN);

        // use PictureSelector
//        PictureSelectorConfig.initMultiConfig(AddCircleActivity.this, maxTotal);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // for Matisse
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == RESULT_OK) {
            refreshAdapter(Matisse.obtainResult(data));
        }

        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST);
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAdapter.notifyDataSetChanged();
        }
    }

    // for Matisse
    private void refreshAdapter(List<Uri> uris) {
        for (Uri uri : uris) {
            String path = uri.toString();
            mPicList.add(path);
            mGridViewAdapter.notifyDataSetChanged();
        }
    }


    private void submitEvent(Context theActivity, String title, String content, String publisher, String location, String time) {
        final Gson gson = new Gson();

        ArrayList<String> imgs = new ArrayList<>();

        for (String s : mPicList) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(theActivity.getContentResolver(), Uri.parse(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgs.add(Bitmap2StrByBase64(bitmap));
        }

        CircleBean circle = new CircleBean(title, content, publisher, imgs, location, time);

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

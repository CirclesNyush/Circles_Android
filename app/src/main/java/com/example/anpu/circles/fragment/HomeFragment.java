package com.example.anpu.circles.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.anpu.circles.R;
import com.example.anpu.circles.model.InfoBean;
import com.example.anpu.circles.model.User;
import com.example.anpu.circles.model.UserAvatar;
import com.example.anpu.circles.model.UserData;
import com.example.anpu.circles.model.UserResponseStatus;
import com.example.anpu.circles.utilities.GlideV4ImageEngine;
import com.example.anpu.circles.utilities.ImageHelper;
import com.example.anpu.circles.utilities.PermissonHelper;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by anpu on 2018/3/5.
 */
public class HomeFragment extends Fragment {

//    @BindView(R.id.get_token) Button getToken;


    private Uri selectAvatar;

    private Window window;
    private android.support.v7.widget.Toolbar toolbar;
    private TextView personal_description, cell, nickname, email;
    private Button btn_edit, btn_save;


    private int REQUEST = 1001;
    ImageButton pfp;

    private String urlUpdateAvatar = "http://steins.xin:8001/personal/updateavatar";
    private String fetchAvatar = "http://steins.xin:8001/personal/getinfo";
    private String getInfoUrl = "/personal/getinfo";
    private String updateInfoUrl = "/personal/updateinfo";

    private String baseUrl = "http://steins.xin:8001";

    public static final RequestOptions options = new RequestOptions()
            .centerCrop()
            .skipMemoryCache(true)
            .placeholder(R.drawable.ic_book_black_24dp)
            .error(R.drawable.ic_arrow_back)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .circleCrop()
            .priority(Priority.HIGH);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.activity_personal_information_two, container, false);

        initView(rootView);
        bindListener();

        Glide.with(Objects.requireNonNull(getActivity()))
                .load(baseUrl + UserData.getAvatar())
                .apply(options)
                .into(pfp);

        getInfo();


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PermissonHelper.askStroage(getActivity());
        PermissonHelper.askCamera(getActivity());
        pfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(HomeFragment.this)
                        .choose(MimeType.allOf())
                        .countable(false)
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true, "com.example.anpu.circles.fragment.HomeFragment"))
                        .maxSelectable(1)
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.picker))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideV4ImageEngine())
                        .forResult(REQUEST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            selectAvatar = Matisse.obtainResult(data).get(0);
            Log.d("Matisse", "mSelected: " + selectAvatar);
            try {
                Bitmap avatar = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectAvatar);
                uploadAvatar(avatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private void uploadAvatar(Bitmap bitmap) {
        final Gson gson = new Gson();
        String base = ImageHelper.Bitmap2StrByBase64(bitmap);
        UserAvatar avatar = new UserAvatar(base, UserData.getEmail());
        String avatarJson = gson.toJson(avatar);
        // post to the server
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), avatarJson);
        Request request = new Request.Builder()
                .post(body)
                .url(urlUpdateAvatar)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failure to connect to the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ans = response.body().string();
                final UserResponseStatus userResponseStatus = gson.fromJson(ans, UserResponseStatus.class);
                if (userResponseStatus.getStatus() == 0) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Account is not activated.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                    else {
                    String updateTime = String.valueOf(System.currentTimeMillis());
                    UserData.setAvatar(userResponseStatus.getAvatar());
                    //TODO : add signature

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(HomeFragment.this)
                                        .load(baseUrl + UserData.getAvatar())
                                        .apply(options)
                                        .into(pfp);
                            }
                        });
                    }
                }
        });
    }


    private void getInfo() {
        final Gson gson = new Gson();
        User user = new User(UserData.getEmail());
        String userJson = gson.toJson(user);

        Log.d("json", userJson);
        // post to the server
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson);
        Request request = new Request.Builder()
                .post(body)
                .url(baseUrl+getInfoUrl)
                .build();

        //receive from server
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failure to connect to the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ans = response.body().string();
                final InfoBean infoBean = gson.fromJson(ans, InfoBean.class);
                if (infoBean.getStatus() == 0) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Account is not activated.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    //TODO : add signature
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nickname.setText(infoBean.getData().getNickname());
                            cell.setText(infoBean.getData().getPhone());
                            personal_description.setText(infoBean.getData().getDescription());
                            email.setText(infoBean.getData().getEmail());
                        }
                    });
                }
            }
        });
    }

    private void initView(View rootView) {
        window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        toolbar = rootView.findViewById(R.id.personal_toolbar_two);
        ((AppCompatActivity) getActivity()).setSupportActionBar( toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 20);

        pfp = rootView.findViewById(R.id.personal_pfp);

        nickname = rootView.findViewById(R.id.personal_nickname);
        cell = rootView.findViewById(R.id.personal_cell);
        email = rootView.findViewById(R.id.personal_email);
        personal_description = rootView.findViewById(R.id.personal_description_test);
    }

    private void updateInfo(String nickname, String phone, String description, String email) {
        final Gson gson = new Gson();

        InfoBean.DataBean data = new InfoBean.DataBean(nickname, phone, description, email);
        InfoBean info = new InfoBean(UserData.getEmail(), data);

        String infoString = gson.toJson(info);
        // post to the server
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoString);
        Request request = new Request.Builder()
                .post(body)
                .url(baseUrl + updateInfoUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failure to connect to the server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ans = response.body().string();
                final UserResponseStatus userResponseStatus = gson.fromJson(ans, UserResponseStatus.class);
                if (userResponseStatus.getStatus() == 0) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Account is not activated.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getInfo();
                            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void bindListener() {
        nickname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                updateInfo(textView.getText().toString(), "", "", UserData.getEmail());
                nickname.clearFocus();
                return false;
            }
        });

        cell.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                updateInfo("", textView.getText().toString(), "", UserData.getEmail());
                cell.clearFocus();
                return false;
            }
        });

        personal_description.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                updateInfo("", "", textView.getText().toString(), UserData.getEmail());
                personal_description.clearFocus();
                return false;
            }
        });
    }
}

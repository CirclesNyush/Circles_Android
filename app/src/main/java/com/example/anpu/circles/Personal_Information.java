package com.example.anpu.circles;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;





/**
 * Created by Hansa on 2/18/18.
 */

public class Personal_Information extends AppCompatActivity {

    private ActionBar bar;
    private Window window;
    private Toolbar toolbar;
    private TextView textview_personal, cell, nickname, email;
    ImageButton pfp;
    String ImageDecode;
    private int GALLERY = 1, CAMERA = 2;




    private String url_personal_upload = "http://steins.xin:8001/personal/updateinfo";
    private String url_personal_download = "http://steins.xin:8001/personal/getinfo";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //transparent status bar
        window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);

        setContentView(R.layout.activity_personal_information_two);

        toolbar = (Toolbar) findViewById(R.id.personal_toolbar_two);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 20);

        pfp = (ImageButton) findViewById(R.id.personal_pfp);


        //upload info (Temporary information)
//        final Gson gson = new Gson();
//        Personal person = new Personal();
//        String jsonUser = gson.toJson(person);
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonUser);
//        Request request = new Request.Builder()
//                .post(body)
//                .url(url_personal_upload)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Personal_Information.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        textview_personal.setText("oh no");
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                UserResponseStatus userStatus = gson.fromJson(response.body().string(), UserResponseStatus.class);
//
//                if (userStatus.getStatus() == 1) {
//
//                    Personal_Information.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Toast.makeText(Personal_Information.this, "Success.", Toast.LENGTH_LONG).show();
//
//                        }
//                    });
//                }
//
//                else {
//                    Toast.makeText(Personal_Information.this, "Failed to get info.", Toast.LENGTH_LONG).show();
//                }
//            }
//        });



        //download info (Temp)
//        final Gson gson_d = new Gson();
//        OkHttpClient client_d = new OkHttpClient();
//        //RequestBody body_d = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonUser);
//        Request request_d = new Request.Builder()
//                //.post(body)
//                .url(url_personal_download)
//                .build();
//
//        client_d.newCall(request_d).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Personal_Information.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        textview_personal.setText("oh no");
//                    }
//                });
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                UserResponseStatus userStatus = gson_d.fromJson(response.body().string(), UserResponseStatus.class);
//
//                if (userStatus.getStatus() == 1) {
//
//                    final Personal person = gson_d.fromJson(response.body().string(), Personal.class);
//
//                    Personal_Information.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Toast.makeText(Personal_Information.this, "Success.", Toast.LENGTH_LONG).show();
//                            textview_personal = (TextView) findViewById(R.id.personal_description_test);
//                            textview_personal.setText(person.getPersonal_description());
//                            cell = (TextView) findViewById(R.id.personal_cell);
//                            cell.setText(person.getCell());
//                            nickname = (TextView) findViewById(R.id.personal_nickname);
//                            nickname.setText(person.getUsername());
//                            email = (TextView) findViewById(R.id.personal_email);
//                            email.setText(person.getEmail());
//
//                        }
//                    });
//                }
//
//                else {
//                    Toast.makeText(Personal_Information.this, "Failed to get info.", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    /*
        //go back button
        btn_back = (Button)findViewById(R.id.personal_button_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
      */
    }


    public void onClick_pfp (View view) {
        showPictureDialog();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent,GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "Hansa");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(Personal_Information.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    pfp.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Personal_Information.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            pfp.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(Personal_Information.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


}
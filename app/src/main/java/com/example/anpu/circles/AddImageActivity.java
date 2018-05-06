package com.example.anpu.circles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anpu.circles.adapter.ViewPagerAdapter;
import com.example.anpu.circles.utilities.CancelOrOkDialog;
import com.example.anpu.circles.utilities.MainConstant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddImageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

//    private ViewPager viewPager;
//    private TextView positionTv;
    private ArrayList<String> imgList;
    private int mPosition;
    private ViewPagerAdapter mAdapter;

    @BindView(R.id.back_iv) ImageView backIv;
    @BindView(R.id.delete_iv) ImageView deleteIv;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.position_tv) TextView positionTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        ButterKnife.bind(this);

        imgList = getIntent().getStringArrayListExtra(MainConstant.IMG_LIST);
        mPosition = getIntent().getIntExtra(MainConstant.POSITION, 0);
        initView();
    }

    private void initView() {
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        positionTv = (TextView) findViewById(R.id.position_tv);
//        findViewById(R.id.back_iv).setOnClickListener(this);
        backIv.setOnClickListener(this);
//        findViewById(R.id.delete_iv).setOnClickListener(this);
        deleteIv.setVisibility(View.VISIBLE);
        deleteIv.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);

        mAdapter = new ViewPagerAdapter(this, imgList);
        viewPager.setAdapter(mAdapter);
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        positionTv.setText(position + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                back();
                break;
            case R.id.delete_iv:
                System.out.println("delete clicked!!!!!!!!");
                deletePic();
                break;
        }
    }

    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        setResult(MainConstant.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }

    private void deletePic() {
        CancelOrOkDialog dialog = new CancelOrOkDialog(this, "Delete this image?") {
            @Override
            public void ok() {
                super.ok();
                imgList.remove(mPosition);
                if (imgList.size() == 0) {
                    back();
                }
                setPosition();
                dismiss();
            }
        };
        dialog.show();
    }

    private void setPosition() {
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // back key pressed
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

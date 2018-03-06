package com.example.anpu.circles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.example.anpu.circles.adapter.ViewPagerFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anpu on 2018/3/5.
 */

public class MainActivityFragment extends FragmentActivity {

    @BindView(R.id.bottom_bar) BottomNavigationView bnv;
    @BindView(R.id.id__main_viewpager) ViewPager viewPager;

    private android.support.v4.app.Fragment tabHome, tabGroup, tabSettings;

    List<android.support.v4.app.Fragment> fragmentList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        // bind all things
        ButterKnife.bind(this);

        // initialize home as the first window
//        setTabSelection(0);

        initFragmentList();
        initViewPager();


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.group_nav:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.setting_nav:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });
    }

    private void initFragmentList() {
        tabHome = new HomeFragment();
        tabGroup = new GroupFragment();
        tabSettings = new SettingsFragment();
        fragmentList.add(tabHome);
        fragmentList.add(tabGroup);
        fragmentList.add(tabSettings);
    }

    private void initViewPager() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(fragmentManager, fragmentList);
        viewPager.addOnPageChangeListener(new ViewPagerOnPagerChangedListener());
        viewPager.setAdapter(viewPagerFragmentAdapter);
        viewPager.setCurrentItem(0);
    }

    class ViewPagerOnPagerChangedListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

//    public void setTabSelection(int tabSelection) {
//        // start a fragment transaction
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        // hide all fragments in case some overlap
//        hideFragments(fragmentTransaction);
//        switch (tabSelection) {
//            case 0:
//                if (tabHome == null) {
//                    tabHome = new HomeFragment();
//                    fragmentTransaction.add(R.id.id_content, tabHome);
//                }
//                else {
//                    fragmentTransaction.show(tabHome);
//                }
//                break;
//            case 1:
//                if (tabGroup == null) {
//                    tabGroup = new GroupFragment();
//                    fragmentTransaction.add(R.id.id_content, tabGroup);
//                }
//                else {
//                    fragmentTransaction.show(tabGroup);
//                }
//                break;
//            case 2:
//                if (tabSettings == null) {
//                    tabSettings = new SettingsFragment();
//                    fragmentTransaction.add(R.id.id_content, tabSettings);
//                }
//                else {
//                    fragmentTransaction.show(tabSettings);
//                }
//                break;
//        }
//        fragmentTransaction.commit();
//    }

//    private void hideFragments(FragmentTransaction fragmentTransaction) {
//        if (tabHome != null) {
//            fragmentTransaction.hide(tabHome);
//        }
//        if (tabGroup != null) {
//            fragmentTransaction.hide(tabGroup);
//        }
//        if (tabSettings != null) {
//            fragmentTransaction.hide(tabSettings);
//        }
//    }
}

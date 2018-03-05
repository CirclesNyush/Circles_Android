package com.example.anpu.circles;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anpu on 2018/3/5.
 */

public class MainActivityFragment extends FragmentActivity {

    @BindView(R.id.bottom_bar) BottomNavigationView bnv;

    private Fragment tabHome, tabGroup, tabSettings;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        // bind all things
        ButterKnife.bind(this);

        setTabSelection(0);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        setTabSelection(0);
                        break;
                    case R.id.group_nav:
                        setTabSelection(1);
                        break;
                    case R.id.setting_nav:
                        setTabSelection(2);
                        break;
                }
                return false;
            }
        });
    }

    public void setTabSelection(int tabSelection) {
        // start a fragment transaction
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // hide all fragments in case some overlap
        hideFragments(fragmentTransaction);
        switch (tabSelection) {
            case 0:
                if (tabHome == null) {
                    tabHome = new HomeFragment();
                    fragmentTransaction.add(R.id.id_content, tabHome);
                }
                else {
                    fragmentTransaction.show(tabHome);
                }
                break;
            case 1:
                if (tabGroup == null) {
                    tabGroup = new GroupFragment();
                    fragmentTransaction.add(R.id.id_content, tabGroup);
                }
                else {
                    fragmentTransaction.show(tabGroup);
                }
                break;
            case 2:
                if (tabSettings == null) {
                    tabSettings = new SettingsFragment();
                    fragmentTransaction.add(R.id.id_content, tabSettings);
                }
                else {
                    fragmentTransaction.show(tabSettings);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (tabHome != null) {
            fragmentTransaction.hide(tabHome);
        }
        if (tabGroup != null) {
            fragmentTransaction.hide(tabGroup);
        }
        if (tabSettings != null) {
            fragmentTransaction.hide(tabSettings);
        }
    }
}

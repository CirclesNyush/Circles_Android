package com.example.anpu.circles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


public class HomePage1 extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bnv;
    private ViewPager viewpager;
    private SlideAdapter slideadapter;

    // test log out
    SharedPreferences sprefHome;
    SharedPreferences.Editor editorHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

//        viewpager = (ViewPager) findViewById(R.id.pageviewer);
//        slideadapter = new SlideAdapter(this);
//        viewpager.setAdapter(slideadapter);

        sprefHome = PreferenceManager.getDefaultSharedPreferences(this);
        editorHome = sprefHome.edit();

        bnv = (BottomNavigationView) findViewById(R.id.bottomdrawer);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.group_nav:
                        navGroup();
                        Toast.makeText(getApplicationContext(),"you selected Group",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home_nav:
                        //replace this with intent
                        Toast.makeText(getApplicationContext(),"you selected Home",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting_nav:

                        // only to test log out
                        editorHome.putBoolean("login", false);
                        editorHome.commit();
                        Intent intent = new Intent(HomePage1.this, LogInActivity.class);
                        startActivity(intent);
                        HomePage1.this.finish();


                        //replace this with intent
                        Toast.makeText(getApplicationContext(),"you selected settings",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"you cheated",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;

            }
        });


    }

    public void navGroup () {
        Intent intent = new Intent(this,Group.class);
        startActivity(intent);

    }

}

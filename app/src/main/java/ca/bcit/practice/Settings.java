package ca.bcit.practice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;
import java.util.Set;

public class Settings extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private String LANG_CURRENT = "en";
    SharedPreferences preferences1;
    SharedPreferences preferences2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //drawer button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);

        //change state and position
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                Intent home = new Intent(Settings.this, Home.class);
                                startActivity(home);
                                break;

                            case R.id.nav_about:
                                Intent about = new Intent(Settings.this, AboutUs.class);
                                startActivity(about);
                                break;

                            case R.id.nav_settings:
                                Intent setting = new Intent(Settings.this, Settings.class);
                                startActivity(setting);
                                break;

                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LANG_CURRENT.equals("en")) {
                    changeLang(Settings.this, "ko");
                } else {
                    changeLang(Settings.this, "en");
                }
                //finish();
                //recreate();
                startActivity(new Intent(Settings.this, Settings.class));
            }
        });



    }



    //open when tapped
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeLang(Context context, String lang) {
        preferences1 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences1.edit();
        editor.putString("Language", lang);
        editor.apply();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        preferences2 = PreferenceManager.getDefaultSharedPreferences(newBase);
        LANG_CURRENT = preferences2.getString("Language", "en");

        super.attachBaseContext(MyContextWrapper.wrap(newBase, LANG_CURRENT));
    }





}

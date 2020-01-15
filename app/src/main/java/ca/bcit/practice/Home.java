package ca.bcit.practice;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                                Intent home = new Intent(Home.this, Home.class);
                                startActivity(home);
                                break;

                            case R.id.nav_about:
                                Intent about = new Intent(Home.this, AboutUs.class);
                                startActivity(about);
                                break;

                            case R.id.nav_settings:
                                Intent setting = new Intent(Home.this, Settings.class);
                                startActivity(setting);
                                break;

                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        ImageButton button1 = (ImageButton)findViewById(R.id.family);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, MapsActivity.class);
                startActivity(i);
            }
        });

        ImageButton button2 = (ImageButton)findViewById(R.id.education);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(Home.this, MapsActivity2.class);
                startActivity(i2);
            }
        });

        ImageButton button3 = (ImageButton)findViewById(R.id.settlement);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(Home.this, MapsActivity3.class);
                startActivity(i3);
            }
        });

        ImageButton button4 = (ImageButton)findViewById(R.id.housing);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(Home.this, MapsActivity4.class);
                startActivity(i4);
            }
        });

        //add animation to HOME buttons
        ImageButton b1 = (ImageButton)findViewById(R.id.family);
        Animation a1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in);
        b1.startAnimation(a1);

        ImageButton b2 = (ImageButton)findViewById(R.id.education);
        Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in);
        a2.setStartOffset(200);

        b2.startAnimation(a2);

        ImageButton b3 = (ImageButton)findViewById(R.id.settlement);
        Animation a3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in);
        a3.setStartOffset(400);

        b3.startAnimation(a3);

        ImageButton b4 = (ImageButton)findViewById(R.id.housing);
        Animation a4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in);
        a4.setStartOffset(600);

        b4.startAnimation(a4);

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

}
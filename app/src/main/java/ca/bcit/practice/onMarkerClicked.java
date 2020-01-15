package ca.bcit.practice;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

public class onMarkerClicked extends AppCompatActivity {
    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onmarkerclicked);

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
                                Intent home = new Intent(onMarkerClicked.this, Home.class);
                                startActivity(home);
                                break;

                            case R.id.nav_about:
                                Intent about = new Intent(onMarkerClicked.this, AboutUs.class);
                                startActivity(about);
                                break;

                            case R.id.nav_settings:
                                Intent settings = new Intent(onMarkerClicked.this, Settings.class);
                                startActivity(settings);
                                break;

                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        Button b1 = (Button)findViewById(R.id.getDirection);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=Your location&daddr="));
                startActivity(intent);
            }
        });

        String getMTitle = getIntent().getStringExtra("title");
        String getMDesc = getIntent().getStringExtra("desc");

        TextView title = (TextView)findViewById(R.id.mTitle);
        TextView description = (TextView)findViewById(R.id.desc);

        title.setText(getMTitle);
        description.setText(getMDesc);


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

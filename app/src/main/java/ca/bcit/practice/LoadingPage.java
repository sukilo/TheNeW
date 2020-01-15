package ca.bcit.practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoadingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        //adding animation to image view
        ImageView logo = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        logo.startAnimation(animation);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    finish();
                    super.run();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        timer.start();
    }
}

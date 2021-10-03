package com.eded.androidap.listvieweded;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class welcome extends Activity {

    private static boolean splashLoaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          if (!splashLoaded) {
            setContentView(R.layout.activity_welcome);
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(welcome.this, MainActivity.class));
                    finish();
                }
            }, secondsDelayed * 300);

            splashLoaded = true;
        }
        else {
            Intent goToMainActivity = new Intent(welcome.this, MainActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }
    }
}






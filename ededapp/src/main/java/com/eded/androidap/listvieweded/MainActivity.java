package com.eded.androidap.listvieweded;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {
   public static  int switcher=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Stetho.initializeWithDefaults(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener(nave);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new Home_fragment()).commit();


    }//onCreate end


    private BottomNavigationView.OnNavigationItemSelectedListener nave =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedfragment = null;

                    switch (item.getItemId()){
                        case R.id.action_home:
                            switcher=1;
                            selectedfragment=new Home_fragment();
                            break;
                        case R.id.action_mostuse:
                            selectedfragment=new progress_fragment();
                            break;
                        case R.id.action_epiredproduct:
                            selectedfragment=new order_product_fragment();
                            break;
                    }
         getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedfragment).commit();
                    return true;
                }
            };
}//main end

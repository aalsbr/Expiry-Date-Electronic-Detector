package com.eded.androidap.listvieweded.mListView;


import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.eded.androidap.listvieweded.R;


public  class viewholder_fav {

    TextView product_name;
    TextView product_date;
     Button button_image ;
    Button button_image1 ;

    public viewholder_fav(View v) {
        product_name= v.findViewById(R.id.name_l);
        product_date= v.findViewById(R.id.date_time);
        button_image=   v.findViewById(R.id.button_image1);
        button_image1=   v.findViewById(R.id.button_image2);

    }




}

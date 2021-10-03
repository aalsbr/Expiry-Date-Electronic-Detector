package com.eded.androidap.listvieweded.mListView;


import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eded.androidap.listvieweded.R;


public  class viewholder_most  {

    TextView product_name;
    TextView progress_presentage;
    ProgressBar progressBar ;

    public viewholder_most(View v) {

        product_name= v.findViewById(R.id.most_wanted_product);
        progress_presentage= v.findViewById(R.id.progress_presentage);
        progressBar=v.findViewById(R.id.progress_bar);

    }




}

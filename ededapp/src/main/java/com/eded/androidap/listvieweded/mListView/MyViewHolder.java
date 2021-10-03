package com.eded.androidap.listvieweded.mListView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.eded.androidap.listvieweded.R;


public  class MyViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener {
    TextView nameTxt;
    TextView dateTxt;
    MyLongClickListener longClickListener;
    TextView stateTxt;
    ImageView img ;
    ImageView circle_ic ;
    ImageView state_ic ;


    public MyViewHolder(View v) {

        nameTxt= (TextView) v.findViewById(R.id.nameTxt);
        dateTxt = (TextView)  v.findViewById (R.id.DateTXt);
        stateTxt = (TextView)  v.findViewById (R.id.state);
        img= (ImageView) v.findViewById(R.id.image_view);
        circle_ic= (ImageView) v.findViewById(R.id.circle_shape);
        state_ic=(ImageView) v.findViewById(R.id.stateic);
        v.setOnLongClickListener(this);
        v.setOnCreateContextMenuListener(this);
    }


    @Override
    public boolean onLongClick(View v) {
        this.longClickListener.onItemLongClick();
        return false;
    }

    public void setLongClickListener(MyLongClickListener longClickListener)
    {
        this.longClickListener=longClickListener;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(" : ");
        menu.add(0,1,2,"تجديد التاريخ");
        menu.add(0,3,2,"استهلاك المنتج");
        menu.add(0,2,2,"حذف المنتج");




    }



}

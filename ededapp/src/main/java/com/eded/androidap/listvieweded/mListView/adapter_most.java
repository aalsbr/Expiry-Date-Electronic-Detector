package com.eded.androidap.listvieweded.mListView;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.eded.androidap.listvieweded.R;
import com.eded.androidap.listvieweded.mDataObject.Spacecraft;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adapter_most extends BaseAdapter  {

    Context c;
    ArrayList<Spacecraft> spacecrafts;
    LayoutInflater inflater;
    Spacecraft spacecraft;
    public adapter_most(Context c, ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;

    }
    @Override
    public int getCount () {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return spacecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {




        View view = convertView;
         viewholder_most holder=null;
        if(convertView==null) {

               inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.progress_model, parent, false);
                view.setTag(holder);

            }

        else {
            holder = (viewholder_most) view.getTag();
        }

        float  progress_set = Math.round(Float.parseFloat(spacecrafts.get(position).getTotalpresentage()));
        System.out.println("total of adabter "+spacecrafts.get(position).getTotalpresentage());
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(progress_set);
        int formatedd= Integer.parseInt(formatted);
        //Create object from my view holder to set the data in the xml
        holder=new viewholder_most(view);
        notifyDataSetChanged();
        holder.product_name.setText(spacecrafts.get(position).getName());
        holder.progress_presentage.setText(formatedd+"%");
        if(formatedd<20){
            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#00B33C")));
            holder.progressBar.setProgress(formatedd);
        }
        else if (formatedd<50){

            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
            holder.progressBar.setProgress(formatedd);

        }
        else {

            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#d40000")));
            holder.progressBar.setProgress(formatedd);
        }




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                v.setSelected(true);
                Toast.makeText(c, spacecrafts.get(position).getName()+"  "+
                                spacecrafts.get(position).getDate()
                        , Toast.LENGTH_SHORT).show();}

        });
        return view;
    }
    public void updatemassege(){
        Toast.makeText(c, "تم التعديل ", Toast.LENGTH_SHORT).show();
    }

    //EXPOSE NAME AND ID
    public int getSelectedItemID()
    {
        return spacecraft.getId();
    }
    public String getSelectedItemName()
    {
        return spacecraft.getName();
    }

    public String getSelectedDate (){
        return spacecraft.getDate();
    }

    public String getSelectedFinish (){
        return spacecraft.getFinishnumber();}

    public String getSelectedExpired (){
        return spacecraft.getExpirednumber();}

        public String getSelectedOntime(){
        return spacecraft.getOntimenumber();
        }















    }

















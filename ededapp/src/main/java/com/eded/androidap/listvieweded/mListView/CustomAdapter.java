package com.eded.androidap.listvieweded.mListView;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eded.androidap.listvieweded.R;
import com.eded.androidap.listvieweded.mDataObject.Spacecraft;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.eded.androidap.listvieweded.MainActivity.switcher;

public class CustomAdapter extends BaseAdapter  {

    Context c;
    ArrayList<Spacecraft> spacecrafts;
    LayoutInflater inflater;
    Spacecraft spacecraft;
    public CustomAdapter(Context c, ArrayList<Spacecraft> spacecrafts) {
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
         MyViewHolder holder=null;
        if(convertView==null) {

                inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.model, parent, false);
                view.setTag(holder);
        }
        else {
            holder = (MyViewHolder) view.getTag();

        }

        holder=new MyViewHolder(view);
        notifyDataSetChanged();
        holder.nameTxt.setText(spacecrafts.get(position).getName());
        //STATUS COLOR
        if(spacecrafts.get(position).getStates().equals("منتهي الصلاحية")){
            holder.state_ic.setImageResource(R.drawable.ic_quit);
            holder.circle_ic.setColorFilter(Color.parseColor("#FFE7233D"));
            holder.stateTxt.setTextColor(Color.parseColor("#FFE7233D"));
            holder.stateTxt.setText(spacecrafts.get(position).getStates());
        }
        else  if(spacecrafts.get(position).getStates().equals( "نفذت الكمية      ")){
            holder.state_ic.setImageResource(R.drawable.time_s);
            holder.circle_ic.setColorFilter(Color.parseColor("#FFA500"));
            holder.stateTxt.setTextColor(Color.parseColor("#FFA500"));
            holder.stateTxt.setText(spacecrafts.get(position).getStates());

        }
        else{
            holder.state_ic.setImageResource(R.drawable.ic_correct);
            holder.circle_ic.setColorFilter(Color.parseColor("#FF6EB609"));
            holder.stateTxt.setTextColor(Color.parseColor("#FF6EB609"));
            holder.stateTxt.setText(spacecrafts.get(position).getStates());

        }
        if (spacecrafts.get(position).getStates().equals( "منتهي الصلاحية")){
            holder.dateTxt.setText("  "+"    -");
        }
        else if(spacecrafts.get(position).getStates().equals( "نفذت الكمية      ")){
            holder.dateTxt.setText("  "+"    -");
        }
        else{
            holder.dateTxt.setText(Datecalc(spacecrafts.get(position).getDate())+ " يوم متبقي");

        }
        //set image for product
     if(spacecrafts.get(position).getRadiovalue().equals("اكل")){
            holder.img.setImageResource(R.drawable.ic_restaurant_menu_black_24dp);

        }

       else if(spacecrafts.get(position).getRadiovalue().equals("مشروب")){
            holder.img.setImageResource(R.drawable.ic_wine);

        }

        else if(spacecrafts.get(position).getRadiovalue().equals("دواء")){
            holder.img.setImageResource(R.drawable.ic_injection);

        }
















        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onItemLongClick() {
                spacecraft= (Spacecraft) getItem(position);
            }
        });

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



    public long Datecalc(String date){

        try {

            Calendar calendar = Calendar.getInstance();
            long i = 0;
            Date current_date = null;
            Date date_after = null;
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String currentDate1= df.format(calendar
                    .getTime());

            current_date =  df.parse(currentDate1);
            date_after = df.parse(date);
            if(date_after.getTime()>=current_date.getTime()){
            long diff = Math.abs( date_after.getTime()-current_date.getTime());
            long diffDays = diff / (24 * 60 * 60 * 1000);
            return diffDays;}

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return 0;


    }















    }

















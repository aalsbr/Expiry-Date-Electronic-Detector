package com.eded.androidap.listvieweded.mListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eded.androidap.listvieweded.R;
import com.eded.androidap.listvieweded.mDataBase.DBAdapter;
import com.eded.androidap.listvieweded.mDataObject.Spacecraft;
import com.eded.androidap.listvieweded.mDataObject.Spacecraft2;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;



public class adapter_fav extends BaseAdapter  {
    private RequestQueue requestqueue;
    private StringRequest stringRequest;
    private String url = "http://87.101.174.3:8081/";
    Context c;
    ArrayList<Spacecraft2> spacecrafts;
    ArrayList<Spacecraft> spacecraftsf = new ArrayList<>();
    LayoutInflater inflater;
    Spacecraft2 spacecraft;
    EditText nameEditText;
    int id5=0 ;
    int productname;
    Button saveBtn;
    ListView lvproduct;
    EditText date;
    TextView radio_type;
    RadioGroup radioGroup;
    RadioButton radioButton;
    public adapter_fav(Context c, ArrayList<Spacecraft2> spacecrafts) {
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
         viewholder_fav holder=null;
         if(convertView==null) {

               inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.orderd_model, parent, false);
                view.setTag(holder);

            }

        else {
            holder = (viewholder_fav) view.getTag();
        }


        //Create object from my view holder to set the data in the xml
        holder=new viewholder_fav(view);
        notifyDataSetChanged();
        holder.product_name.setText(spacecrafts.get(position).getProduct_name());

       holder.product_date.setText(" منذ "+Datecalc(spacecrafts.get(position).getDate_product())+" يوم ");
        holder.button_image1.setText("حذف");
        holder.button_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAdapter db = new DBAdapter(v.getContext());
                requestqueue = Volley.newRequestQueue(v.getContext());
                new AlertDialog.Builder(v.getContext())
                        .setMessage("هل تريد بالفعل حذف الطلب ؟")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //GET ID
                                int id = spacecrafts.get(position).getId();
                                        //DELETE FROM DB
                                            db.openDB();
                                            boolean deleted2=db.delete2(id);
                                            db.closeDB();
                                            spacecrafts.remove(spacecrafts.get(position));
                                            notifyDataSetChanged();
                                            Toast.makeText(v.getContext(), "تم حذف المنتج", Toast.LENGTH_SHORT).show();



                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }

        });
        if(spacecrafts.get(position).getProduct_yes_no().equals("No")){
            holder.button_image.setTag(spacecrafts.get(position));
            holder.button_image.setText("اضافة ");
            holder.button_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog d = new Dialog(v.getContext());
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    d.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    d.setContentView(R.layout.dialog_layout1);
                    //hiding floating button
                    // declare buttons
                    nameEditText = (EditText) d.findViewById(R.id.nameEditTxt5);
                    nameEditText.setText(spacecrafts.get(position).getProduct_name());
                    radio_type = d.findViewById(R.id.radiotype5);
                    date = (EditText) d.findViewById(R.id.dateText5);
                    radioGroup= (RadioGroup) d.findViewById(R.id.radio_button5);
                    date.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {

                                DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
                                datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
                                    @Override
                                    public void onDateChoose(int year, int month, int day) {

                                        date.setText(year + "/" + month + "/" + day);
                                        if (Datecalc1(date.getText().toString()) == -1) {
                                            date.requestFocus();
                                            date.setError("تاريخ المنتج منتهي الصلاحيه");
                                        }

                                    }
                                });
                                datePickerDialogFragment.show(getActivity(v.getContext()).getFragmentManager(), "DatePickerDialogFragment");
                                return true;
                            }
                            return false;
                        }
                    });




                    saveBtn = (Button) d.findViewById(R.id.saveBtn5);
                    // if add button clicked do this ...
                    //if user click save button do that ....
                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            // find the radiobutton by returned id
                            radioButton = (RadioButton) d.findViewById(selectedId);
                            productname = 1;
                            //if user forget to enter product name
                            if (TextUtils.isEmpty(nameEditText.getText())) {
                                nameEditText.setError("ادخل اسم المنتج !");
                            }
                            //if user enter product name that is already exist

                            // if user forget to enter date
                            if (TextUtils.isEmpty(date.getText())) {
                                date.setError("ادخل تاريخ الانتهاء !");
                            } else if (TextUtils.isEmpty(nameEditText.getText())) {
                                nameEditText.setError("ادخل اسم المنتج !");
                            } else  if (Datecalc1(date.getText().toString()) == 0) {
                                date.requestFocus();
                                date.setError("تاريخ المنتج منتهي الصلاحيه");

                            }

                            if(radioGroup.getCheckedRadioButtonId()== -1){
                                Toast.makeText(v.getContext(), " ادخل نوع المنتج! ", Toast.LENGTH_SHORT).show();
                                return;

                            }

                            //save if user type all correctly
                            else if ((!TextUtils.isEmpty(nameEditText.getText()) &&
                                    !TextUtils.isEmpty(date.getText()) &&
                                    radioGroup.getCheckedRadioButtonId()!= -1&&
                                    Datecalc1(date.getText().toString()) != 0

                                      )

                            ) {
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String currentDate1 = df.format(calendar
                                        .getTime());

                                String a = "0" + nameEditText.getText().toString()+"?"+""+0+""+"?"+ date.getText().toString() ;
                                //save

                                requestqueue = Volley.newRequestQueue(v.getContext());
                                stringRequest = new StringRequest(Request.Method.GET, url + a, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        DBAdapter db = new DBAdapter(v.getContext());
                                        db.openDB();
                                        boolean saved = db.add(nameEditText.getText().toString(),date.getText().toString(),"صالح للاستخدام ",
                                                "0","0","0","0", radioButton.getText().toString());
                                        db.closeDB();
                                        if (saved) {
                                            DBAdapter db1 = new DBAdapter(v.getContext());
                                            int id = spacecrafts.get(position).getId();
                                            //DELETE FROM DB
                                            db1.openDB();
                                            boolean deleted2=db1.delete2(id);
                                            db1.closeDB();
                                            spacecrafts.remove(spacecrafts.get(position));
                                            notifyDataSetChanged();

                                            Toast.makeText(v.getContext(), "تم الحفظ ", Toast.LENGTH_SHORT).show();}
                                        else {
                                            Toast.makeText(v.getContext(), "لم يتم الحفظ", Toast.LENGTH_SHORT).show();}


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 5000, 0,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                requestqueue.add(stringRequest);






                                   d.dismiss();}


                            }
                            //close dialog


                        //close onclick method
                    });
                    d.show();

                }});}

       else  if (spacecrafts.get(position).getProduct_yes_no().equals("Yes")){
            holder.button_image.setTag(spacecrafts.get(position));
            holder.button_image.setText("تعديل ");
            holder.button_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //declaring Dialog and setting view
                    Dialog du = new Dialog(v.getContext());
                    du.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    du.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    du.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    du.setContentView(R.layout.dialog_update);
                    DBAdapter db3 = new DBAdapter(v.getContext());
                    spacecraftsf.clear();
                    db3.openDB();
                    Cursor c = db3.retrieve();
                    Spacecraft spacecraft;

                    while (c.moveToNext()) {
                        int id = c.getInt(0);
                        String name2 = c.getString(1);
                        spacecraft = new Spacecraft();
                        spacecraft.setId(id);
                        spacecraft.setName(name2);
                        spacecraftsf.add(spacecraft);
                    }
                    db3.closeDB();



                    // declare buttons
                     TextView nameEditText1 = (TextView) du.findViewById(R.id.nameupdate);
                    EditText date1 = (EditText) du.findViewById(R.id.dateupdate);
                    Button  saveBtn1 = (Button) du.findViewById(R.id.saveupdate);
                    // if update button clicked do this ...
                    nameEditText1.setText( spacecrafts.get(position).getProduct_name() );
                    date1.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
                                datePickerDialogFragment.setOnDateChooseListener((year, month, day) -> {
                                    date1.setText(year + "/" + month + "/" + day);
                                    if (Datecalc1(date1.getText().toString()) == 0) {
                                        date1.requestFocus();
                                        date1.setError("تاريخ المنتج منتهي الصلاحيه");
                                    }
                                });
                                datePickerDialogFragment.show(getActivity(v.getContext()).getFragmentManager(), "DatePickerDialogFragment");
                                return true;
                            }
                            return false;
                        }
                    });
                    saveBtn1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            //save if user type all correctly
                            if (Datecalc1(date1.getText().toString()) == 0) {
                                date1.requestFocus();
                                date1.setError("تاريخ المنتج منتهي الصلاحيه");
                            }

                            else if (Datecalc1(date1.getText().toString()) != 0) {
                                String b = date1.getText().toString();
                                int ontimecount=0;
                                if (Datecalc1(date1.getText().toString())<=3){
                                    ontimecount=1;}
                                else if (Datecalc1(date1.getText().toString())<=7){
                                    ontimecount=2;}
                                else if (Datecalc1(date1.getText().toString())<=14){
                                    ontimecount=4;}
                                else if (Datecalc1(date1.getText().toString())<=30){
                                    ontimecount=8;}
                                else if (Datecalc1(date1.getText().toString())<=60){
                                    ontimecount=16;}
                                else if (Datecalc1(date1.getText().toString())<=90){
                                    ontimecount=32;}
                                else if (Datecalc1(date1.getText().toString())<=120){
                                    ontimecount=64;}
                                else{
                                    ontimecount=100;
                                }

                               // String aa = "1"+"?"+""+ontimecount+""+"?"+date1.getText().toString();


                                for (int i = 0; i < spacecraftsf.size(); i++) {
                                    if (spacecraftsf.get(i).getName().equals(nameEditText1.getText().toString())) {
                                        id5 = i+1;

                                    }
                                    else {
                                        System.out.println("erororrrororo");
                                    }
                                }

                                String aa = "1" + spacecrafts.get(position).getProduct_name()+"?"+""+ontimecount+""+"?"+date1.getText().toString();

                                requestqueue = Volley.newRequestQueue(v.getContext());
                                stringRequest = new StringRequest(Request.Method.GET, url +aa, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        DBAdapter db1 = new DBAdapter(v.getContext());
                                        db1.openDB();
                                        boolean updatestate=db1.updatestate(id5,"صالح للاستخدام ");
                                        boolean updated = db1.updatedate(id5, date1.getText().toString());
                                        db1.closeDB();
                                        if (updated) {
                                            DBAdapter db2 = new DBAdapter(v.getContext());
                                            int id = spacecrafts.get(position).getId();
                                            //DELETE FROM DB
                                            db2.openDB();
                                            boolean deleted2=db2.delete2(id);
                                            db2.closeDB();
                                            spacecrafts.remove(spacecrafts.get(position));
                                            notifyDataSetChanged();

                                        } else {
                                            Toast.makeText(v.getContext(), "لم يتم حفظ التعديلات  ", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(v.getContext(), "تاكد من الاتصال بالشبكه ", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 100, 0,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                requestqueue.add(stringRequest);




                                //end of updatedatemethod

                              //  requestqueue = Volley.newRequestQueue(v.getContext());
                               // stringRequest = new StringRequest(Request.Method.GET, url +aa, new Response.Listener<String>() {
                                  //  @Override
                                /*    public void onResponse(String response) {


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(), "تاكد من الاتصال بالشبكه ", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                requestqueue.add(stringRequest);*/




                                du.dismiss();}

                        }
                    });
                    du.show();
                }
            });






        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                v.setSelected(true);
         }

        });
        return view;
    }
    public void updatemassege(){
        Toast.makeText(c, "تم التعديل ", Toast.LENGTH_SHORT).show();
    }

    //EXPOSE NAME AND ID


    public long Datecalc(String date) {

        try {
            Calendar calendar = Calendar.getInstance();
            long i = 0;
            Date current_date = null;
            Date date_after =   null;
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String currentDate1 = df.format(calendar
                    .getTime());

            current_date = df.parse(currentDate1);
            date_after = df.parse(date);
            if (date_after.getTime() <= current_date.getTime()) {
                long diff = Math.abs( current_date.getTime()-date_after.getTime());
                long diffDays = diff / (24 * 60 * 60 * 1000);
                return diffDays;
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return 0;

    }



    public Activity getActivity(Context c ) {
        while (c instanceof ContextWrapper) {
            if (c instanceof Activity) {
                return (Activity)c;
            }
            c = ((ContextWrapper)c).getBaseContext();
        }
        return null;
    }


    public long Datecalc1(String date){

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

















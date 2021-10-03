package com.eded.androidap.listvieweded;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
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
import com.eded.androidap.listvieweded.mDataBase.DBAdapter;
import com.eded.androidap.listvieweded.mDataObject.Spacecraft;
import com.eded.androidap.listvieweded.mListView.CustomAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Home_fragment extends Fragment {
    PieChart pieChart;
    //Request to server intalizing varibles
    RequestQueue requestqueue;
    StringRequest stringRequest;
   // String url = "http://87.101.174.3:8081/";
    //intalizing objects
    int size;
    int productname;
    ListView lv;
    View empty;
    EditText nameEditText;
    TextView radio_type;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText date;
    Button saveBtn;
    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();
    CustomAdapter adapter;
    ArrayList<String> productstate = new ArrayList<>();
    final Boolean forUpdate = true;
    Handler handler = new Handler();
    //varibles for update date;
    TextView nameEditText1;
    EditText date1;
    Button saveBtn1;
    FloatingActionButton fab;
    int fab_hide;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.homee_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar  mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        //declaring tool bar
        //delcaring a list view
        empty = view.findViewById(R.id.emptyTV);
        lv = (ListView) view.findViewById(R.id.lv);
        lv.setEmptyView(empty);
        //delcariing the adapter
        adapter = new CustomAdapter(getContext(), spacecrafts);
        getSpacecrafts();
        lv.setAdapter(adapter);
       // sendrequestupdate("3");
        sortingdata();

        //   sortingdata();
        adapter.notifyDataSetChanged();
        Collections.sort(spacecrafts, new Home_fragment.sortbydate());
        Collections.sort(spacecrafts, new Home_fragment.sortbystatus());






        //            //declaring add product button
         fab = (FloatingActionButton) view.findViewById(R.id.fab);
        assert fab != null;
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    fab.hide();
                }else{
                    fab.show();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog_add();



            }
        });

    }


    private void displayDialog_add() {
         fab.hide();
        //declaring Dialog and setting view
        Dialog d = new Dialog(getContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        d.setContentView(R.layout.dialog_layout);

        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {
                fab.show();
            }
        });



        //hiding floating button
        // declare buttons
        nameEditText = (EditText) d.findViewById(R.id.nameEditTxt);
        radio_type = d.findViewById(R.id.radiotype);
        date = (EditText) d.findViewById(R.id.dateText);
        radioGroup= (RadioGroup) d.findViewById(R.id.radio_button);

        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
                    datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
                        @Override
                        public void onDateChoose(int year, int month, int day) {

                            date.setText(year + "/" + month + "/" + day);
                            if (adapter.Datecalc(date.getText().toString()) == -1) {
                                date.requestFocus();
                                date.setError("تاريخ المنتج منتهي الصلاحيه");
                            }

                        }
                    });
                    datePickerDialogFragment.show(getActivity(getContext()).getFragmentManager(), "DatePickerDialogFragment");
                    return true;
                }
                    return false;
            }
        });

        DBAdapter db = new DBAdapter(getContext());
        saveBtn = (Button) d.findViewById(R.id.saveBtn);
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
                else if (productname == 1) {
                    for (int i = 0; i < spacecrafts.size(); i++) {
                        if (spacecrafts.get(i).getName().equals(nameEditText.getText().toString())) {
                            nameEditText.setError("اسم المنتج موجود ");
                            return;
                        }
                    }
                    productname = 0;
                }
                // if user forget to enter date
                if (TextUtils.isEmpty(date.getText())) {
                    date.setError("ادخل تاريخ الانتهاء !");
                } else if (TextUtils.isEmpty(nameEditText.getText())) {
                    nameEditText.setError("ادخل اسم المنتج !");
                } else  if (adapter.Datecalc(date.getText().toString()) == 0) {
                    date.requestFocus();
                    date.setError("تاريخ المنتج منتهي الصلاحيه");

                }

                if(radioGroup.getCheckedRadioButtonId()== -1){
                    Toast.makeText(getContext(), " ادخل نوع المنتج! ", Toast.LENGTH_SHORT).show();
                    return;

                }

                //save if user type all correctly
                else if ((!TextUtils.isEmpty(nameEditText.getText()) &&
                        !TextUtils.isEmpty(date.getText()) &&
                        radioGroup.getCheckedRadioButtonId()!= -1&&
                        adapter.Datecalc(date.getText().toString()) != 0
                        && productname == 0 || spacecrafts.isEmpty())

                       ) {

                    int ontimecount =0;
                    if (adapter.Datecalc(date.getText().toString())<=3){
                        ontimecount=1;}
                    else if (adapter.Datecalc(date.getText().toString())<=7){
                        ontimecount=2;}
                    else if (adapter.Datecalc(date.getText().toString())<=14){
                        ontimecount=4;}
                    else if (adapter.Datecalc(date.getText().toString())<=30){
                        ontimecount=8;}
                    else if (adapter.Datecalc(date.getText().toString())<=60){
                        ontimecount=16;}
                    else if (adapter.Datecalc(date.getText().toString())<=90){
                        ontimecount=32;}
                    else if (adapter.Datecalc(date.getText().toString())<=120){
                        ontimecount=64;}
                    else{
                        ontimecount=100;
                    }

                    String a = "0" + nameEditText.getText().toString()+"?"+""+ontimecount+""+"?"+ date.getText().toString() ;
                    size = spacecrafts.size();
                    //save
                    save(nameEditText.getText().toString(),date.getText().toString(),radioButton.getText().toString());

                 /*  requestqueue = Volley.newRequestQueue(v.getContext());
                    stringRequest = new StringRequest(Request.Method.GET, url + a, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           //save if connected
                            

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(v.getContext(), "تاكد من الاتصال بالشبكه ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 5000, 0,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestqueue.add(stringRequest);*/
                    d.dismiss();
                }
                //close dialog

            }
            //close onclick method
        });
        d.show();




    }


  /*  public void sendrequestupdate(String number) {
        requestqueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.GET, url + number, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String s1 = response.toString();
                String out = null;
                try {
                    out = new String(s1.getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println("الرد" + out);
                    updatestate(out);
                    Collections.sort(spacecrafts, new Home_fragment.sortbydate());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("cannot send ");

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 5000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestqueue.add(stringRequest);}



    public void requeststatusNumbw() {
        String requeststate = "4" + adapter.getSelectedItemName() + "?" +"a";
        requestqueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.GET, url + requeststate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String s1 = response.toString();
                String out = null;
                try {
                    out = new String(s1.getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println("الرد" + out);
                    updatestatusnumberes(out);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showpiechart();
                System.out.println("cannot send ");
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 5000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestqueue.add(stringRequest);
    }*/

    //save in the database
    private void save(String sname, String sdate,String radio) {
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        boolean saved = db.add(sname, sdate,  "صالح للاستخدام ","0","0","0","0",radio);

        if (saved) {
            getSpacecrafts();
            Collections.sort(spacecrafts, new Home_fragment.sortbydate());
            Toast.makeText(getContext(), "تم الحفظ ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "لم يتم الحفظ", Toast.LENGTH_SHORT).show();
        }
    }

    //select data from database
    public void getSpacecrafts() {
        spacecrafts.clear();
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        Cursor c = db.retrieve();
        Spacecraft spacecraft;
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String date1 = c.getString(2);
            String state = c.getString(3);
            String finish2=c.getString(4);
            String epired2 =c.getString(5);
            String  ontime2 =c.getString(6);
            String totalpresentage=c.getString(7);
            String radiobuttonv=c.getString(8);
            spacecraft = new Spacecraft();
            spacecraft.setId(id);
            spacecraft.setName(name);
            spacecraft.setDate(date1);
            spacecraft.setStates(state);
            spacecraft.setFinishnumber(finish2);
            spacecraft.setExpirednumber(epired2);
            spacecraft.setOntimenumber(ontime2);
            spacecraft.setTotalpresentage(totalpresentage);
            spacecraft.setRadiovalue(radiobuttonv);
            spacecrafts.add(spacecraft);
        }
        db.closeDB();
        Collections.sort(spacecrafts, new Home_fragment.sortbydate());
        lv.setAdapter(adapter);
    }


    //Delete product with long click
    public void delete() {
        DBAdapter db = new DBAdapter(getContext());
        requestqueue = Volley.newRequestQueue(getContext());
        new AlertDialog.Builder(getContext())
                .setMessage("هل تريد بالفعل حذف المنتج؟")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //GET ID
                        int id = adapter.getSelectedItemID();
                        String a = "2" + adapter.getSelectedItemName() +"?"+"a";;


                        db.openDB();
                        boolean deleted = db.delete(id);
                        db.closeDB();
                        if (deleted) {
                              getSpacecrafts();
                             Toast.makeText(getContext(), "تم حذف المنتج", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "لم يتم الحذف ", Toast.LENGTH_SHORT).show();
                        }
                     /*   stringRequest = new StringRequest(Request.Method.GET, url + a, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //DELETE FROM DB
                                db.openDB();
                                boolean deleted = db.delete(id);
                                db.closeDB();
                                if (deleted) {
                                  //  getSpacecrafts();
                                   // Toast.makeText(getContext(), "تم حذف المنتج", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "لم يتم الحذف ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "تاكد من الاتصال بالشبكه ", Toast.LENGTH_SHORT).show();

                            }
                        });
                        requestqueue.add(stringRequest);*/



                    }})
                .setNegativeButton(android.R.string.no, null).show();


    }

    //This method for the long click on items
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        CharSequence title = item.getTitle();
        if (title == "تجديد التاريخ") {
            displaydialog_update();
        }
        else if (title=="استهلاك المنتج"){
           // requeststatusNumbw();
            showpiechart();
        }
        else if (title == "حذف المنتج") {
            delete();
        }

        return super.onContextItemSelected(item);
    }
    public class sortbystatus implements Comparator<Spacecraft> {
        @Override
        public int compare(Spacecraft spacecraft, Spacecraft t1) {

            if (spacecraft.getStates().equals("نفذت الكمية      ")&&!t1.getStates().equals("نفذت الكمية      "))
             {


                    return 1;}

            else if (spacecraft.getStates().equals("نفذت الكمية      ")&&!t1.getStates().equals("نفذت الكمية      ")) {


                return-1;
            }
            else {
                    return 0;

            }

        }

    }


        public class sortbydate implements Comparator<Spacecraft> {
            @Override
            public int compare(Spacecraft spacecraft, Spacecraft t1) {

                if (adapter.Datecalc(spacecraft.getDate()) > adapter.Datecalc(t1.getDate())
                ) {
                    if(spacecraft.getStates().equals("نفذت الكمية      ")&&!t1.getStates().equals("نفذت الكمية      ")) {

                        return -1;
                    }
                   else{

                    return 1;}
                }
                else if (adapter.Datecalc(spacecraft.getDate()) < adapter.Datecalc(t1.getDate())) {

                    if(spacecraft.getStates().equals("نفذت الكمية      ")&&!t1.getStates().equals("نفذت الكمية      ") ){
                        return -1;
                    }
                   else{ return -1;}
                }
                else {
                    if(spacecraft.getStates().equals("نفذت الكمية      ")&&!t1.getStates().equals("نفذت الكمية      ")){
                        return -1;
                    }
                    else{
                    return 0;}
                }



            }

        }//sorybydate end

    //sorybydate end

    public void updatestatusnumberes(String x){
        spacecrafts.clear();
        x = x.substring(0, x.indexOf("!"));
        String splitted[] = x.split(",");
        String finish = splitted[0];
        String epired = splitted[1];
        String ontime = splitted[2];
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        Cursor c = db.retrieve();
        Spacecraft spacecraft;

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name2 = c.getString(1);
            String date2 = c.getString(2);
            String state2 = c.getString(3);
            String finish2=c.getString(4);
            String epired2 =c.getString(5);
            String  ontime2=c.getString(6);
            String  totalprsentage=c.getString(7);

            spacecraft = new Spacecraft();
            spacecraft.setId(id);
            spacecraft.setName(name2);
            spacecraft.setDate(date2);
            spacecraft.setStates(state2);
            spacecraft.setFinishnumber(finish2);
            spacecraft.setExpirednumber(epired2);
            spacecraft.setOntimenumber(ontime2);
            spacecraft.setTotalpresentage(totalprsentage);
            spacecrafts.add(spacecraft);
        }

        int id = adapter.getSelectedItemID();

        boolean updatedstatenumber = db.updatstatenumbers(id,finish,epired,ontime);
        db.closeDB();

        if (updatedstatenumber) {

            int finished = Integer.parseInt(finish.trim());
            int expired = Integer.parseInt(epired.trim());
            int ontimex = Integer.parseInt(ontime.trim());

            //declaring Dialog and setting view
            Dialog piechardialog = new Dialog(getContext());
            piechardialog.setContentView(R.layout.piechart);
            piechardialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            pieChart= (PieChart) piechardialog.findViewById(R.id.piechart);
            pieChart.setUsePercentValues(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5,-10,5,2);
            pieChart.setDragDecelerationFrictionCoef(0.95f);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleRadius(60f);
            ArrayList<PieEntry> productinfo = new ArrayList<>();


            System.out.println("FINISH ="+finish);
            System.out.println("EXPIRED = "+expired);
            System.out.println("ONTIME= "+ontime);


            productinfo.add(new PieEntry(ontimex,"في الوقت"));
            productinfo.add(new PieEntry(expired,"منتهي الصلاحية"));
            productinfo.add(new PieEntry(finished,"نفذت الكمية"));

            PieDataSet infodata = new PieDataSet(productinfo,"");
            infodata.setSliceSpace(3f);
            infodata.setSelectionShift(3f);
            infodata.setColors(Color.parseColor("#00B33C"),
                    Color.parseColor("#d40000"),
                    Color.parseColor("#FFA500"));

            PieData storeinfo = new PieData(infodata);
            storeinfo.setValueTextSize(15f);
            storeinfo.setValueTextColor(Color.YELLOW);
            pieChart.setDrawEntryLabels(false);
            pieChart.setCenterText(adapter.getSelectedItemName());
            pieChart.setCenterTextSize(20);
            pieChart.animateY(1000, Easing.EaseInCubic);
            pieChart.setData(storeinfo);
            piechardialog.show();
            getSpacecrafts();



        } else {
            Toast.makeText(getContext(), "تاكد من الشبكه", Toast.LENGTH_SHORT).show();
        }

    }



    public void updatestate(String x) {
        spacecrafts.clear();
        x = x.substring(0, x.indexOf("!"));
        String splitted[] = x.split(",");
        String namex[] = new String[splitted.length];
        String state[] = new String[splitted.length];
        int counter1 = 0, counter2 = 0;

        for (int i = 0; i < splitted.length; i++) {
            if (i % 2 == 0) {
                namex[counter1] = splitted[i];
                counter1++;
            } else {
                state[counter2] = splitted[i];
                counter2++;
            }

        }
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        Cursor c = db.retrieve();
        Spacecraft spacecraft;

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name2 = c.getString(1);
            String date2 = c.getString(2);
            String state2 = c.getString(3);
            spacecraft = new Spacecraft();
            spacecraft.setId(id);
            spacecraft.setName(name2);
            spacecraft.setDate(date2);
            spacecraft.setStates(state2);
            spacecrafts.add(spacecraft);
        }

        db.closeDB();

        for (int i = 0; i < namex.length - 1; i++) {
            for (int j = 0; j < spacecrafts.size(); j++) {
                if (spacecrafts.get(j).getName().equals(namex[i])) {
                    int id = spacecrafts.get(j).getId();
                    if (state[i].equals("Expired")) {
                        state[i] = "منتهي الصلاحية";

                    }
                    if (state[i].equals("Finish")) {
                        state[i] = "نفذت الكمية      ";

                    }
                    //UPDATE IN DB
                    DBAdapter db1 = new DBAdapter(getContext());
                    db1.openDB();
                    boolean updated1 = db1.updatestate(id, state[i]);
                    db1.closeDB();


                    if (updated1) {
                        getSpacecrafts();
                        Collections.sort(spacecrafts, new Home_fragment.sortbydate()); }//method updatestate end
                        Toast.makeText(getContext(), "تم تعديل حالة بعض المنتجات", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "تاكد من الشبكه", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        }



    public void displaydialog_update() {
        fab.hide();
        //declaring Dialog and setting view
         Dialog du = new Dialog(getContext());
        du.requestWindowFeature(Window.FEATURE_NO_TITLE);
        du.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        du.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        du.setContentView(R.layout.dialog_update);
        du.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                fab.show();
            }
        });
        // declare buttons
        nameEditText1 = (TextView) du.findViewById(R.id.nameupdate);
        date1 = (EditText) du.findViewById(R.id.dateupdate);
        saveBtn1 = (Button) du.findViewById(R.id.saveupdate);
        // if update button clicked do this ...
        nameEditText1.setText( adapter.getSelectedItemName() );
        date1.setText(adapter.getSelectedDate());
        date1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
                    datePickerDialogFragment.setOnDateChooseListener((year, month, day) -> {
                        date1.setText(year + "/" + month + "/" + day);

                    });
                    datePickerDialogFragment.show(getActivity(getContext()).getFragmentManager(), "DatePickerDialogFragment");
                    return true;
                }
                    return false;
            }
        });
        saveBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //save if user type all correctly



                    String b = date1.getText().toString();
                    int ontimecount=0;
                    if (adapter.Datecalc(date1.getText().toString())<=3){
                        ontimecount=0;}
                    else if (adapter.Datecalc(date1.getText().toString())<=7){
                        ontimecount=2;}
                    else if (adapter.Datecalc(date1.getText().toString())<=14){
                        ontimecount=4;}
                    else if (adapter.Datecalc(date1.getText().toString())<=30){
                        ontimecount=8;}
                    else if (adapter.Datecalc(date1.getText().toString())<=60){
                        ontimecount=16;}
                    else if (adapter.Datecalc(date1.getText().toString())<=90){
                        ontimecount=32;}
                    else if (adapter.Datecalc(date1.getText().toString())<=120){
                        ontimecount=64;}
                    else{
                        ontimecount=100;
                    }

                    String aa = "1" + adapter.getSelectedItemName()+"?"+""+ontimecount+""+"?"+date1.getText().toString();
                updatedate(date1.getText().toString());
                    /*
                    requestqueue = Volley.newRequestQueue(v.getContext());
                    stringRequest = new StringRequest(Request.Method.GET, url +aa, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                           // updatedate(date1.getText().toString());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "تاكد من الاتصال بالشبكه ", Toast.LENGTH_SHORT).show();

                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 100, 0,
                                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestqueue.add(stringRequest);*/




                du.dismiss();}


        });
        du.show();
    }//update date method end

    public void updatedate(String dateupdate) {
        int id = adapter.getSelectedItemID();
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        boolean updatestate=db.updatestate(id,"صالح للاستخدام ");
        boolean updated = db.updatedate(id, dateupdate);
        db.closeDB();
        if (updated) {
            adapter.updatemassege();
            Collections.sort(spacecrafts, new Home_fragment.sortbydate());
            getSpacecrafts();
        } else {
            Toast.makeText(getContext(), "لم يتم حفظ التعديلات  ", Toast.LENGTH_SHORT).show();
        }
    }//end of updatedatemethod


    public void showpiechart(){
        String fin =adapter.getSelectedFinish();
        String exp =adapter.getSelectedExpired();
        String ont =adapter.getSelectedOntime();
        System.out.println("FINISH ="+fin);
        System.out.println("EXPIRED = "+exp);
        System.out.println("ONTIME= "+ont);
        int finish = Integer.parseInt(fin.trim());
        int expired = Integer.parseInt(exp.trim());
        int ontime = Integer.parseInt(ont.trim());
        //declaring Dialog and setting view
        Dialog piechardialog = new Dialog(getContext());
        piechardialog.setContentView(R.layout.piechart);
        piechardialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pieChart= (PieChart) piechardialog.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,-10,5,2);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(60f);
        ArrayList<PieEntry> productinfo = new ArrayList<>();
        System.out.println("FINISH ="+finish);
        System.out.println("EXPIRED = "+expired);
        System.out.println("ONTIME= "+ontime);
        productinfo.add(new PieEntry(ontime,"في الوقت"));
        productinfo.add(new PieEntry(expired,"منتهي الصلاحية"));
        productinfo.add(new PieEntry(finish,"نفذت الكمية"));

        PieDataSet infodata = new PieDataSet(productinfo,"");
        infodata.setSliceSpace(3f);
        infodata.setSelectionShift(3f);
        infodata.setColors(Color.parseColor("#00B33C"),
                Color.parseColor("#d40000"),
                Color.parseColor("#FFA500"));

        PieData storeinfo = new PieData(infodata);
        storeinfo.setValueTextSize(15f);
        storeinfo.setValueTextColor(Color.YELLOW);
        pieChart.setDrawEntryLabels(false);
        pieChart.setCenterText(adapter.getSelectedItemName());
        pieChart.setCenterTextSize(20);
        pieChart.animateY(1000, Easing.EaseInCubic);
        pieChart.setData(storeinfo);
        piechardialog.show();}


    public Activity getActivity(Context c ) {
        while (c instanceof ContextWrapper) {
            if (c instanceof Activity) {
                return (Activity)c;
            }
            c = ((ContextWrapper)c).getBaseContext();
        }
        return null;
    }


    public void sortingdata(){

        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        Cursor c = db.retrieve();
        Spacecraft spacecraft;

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String date1 = c.getString(2);
            String state = c.getString(3);
            String finish2=c.getString(4);
            String epired2 =c.getString(5);
            String  ontime2 =c.getString(6);
            String totalpresentage=c.getString(7);
            spacecraft = new Spacecraft();
            spacecraft.setId(id);
            spacecraft.setName(name);
            spacecraft.setDate(date1);
            spacecraft.setStates(state);
            spacecraft.setFinishnumber(finish2);
            spacecraft.setExpirednumber(epired2);
            spacecraft.setOntimenumber(ontime2);
            spacecraft.setTotalpresentage(totalpresentage);
            spacecrafts.add(spacecraft);
        }
        for (int i = 0; i < spacecrafts.size(); i++) {
            int id = spacecrafts.get(i).getId();
            int expired=Integer.parseInt(spacecrafts.get(i).getExpirednumber().trim());
            System.out.println("this is expired "+spacecrafts.get(0).getExpirednumber());
            int ontime= Integer.parseInt(spacecrafts.get(i).getOntimenumber().trim());
            int finish=Integer.parseInt(spacecrafts.get(i).getFinishnumber().trim());
            float  total_progress= expired+ontime+finish;
            System.out.println("thid is total "+total_progress);
            float progress_product=0;
            if(expired!=0){
                progress_product=expired/total_progress;
                progress_product=progress_product*100;
                boolean saved = db.savetotalprsentage(id,""+progress_product+"");}


        }
        db.closeDB();
        getSpacecrafts();


    }






}//main end
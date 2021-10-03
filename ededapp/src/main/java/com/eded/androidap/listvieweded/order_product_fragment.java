package com.eded.androidap.listvieweded;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eded.androidap.listvieweded.mDataBase.DBAdapter;
import com.eded.androidap.listvieweded.mDataObject.Spacecraft2;
import com.eded.androidap.listvieweded.mListView.adapter_fav;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class order_product_fragment extends Fragment {
    RequestQueue requestqueue;
    StringRequest stringRequest;
  //  String url = "http://87.101.174.3:8081/";
    ListView lvproduct;
    ArrayList<Spacecraft2> spacecrafts = new ArrayList<>();
    //ArrayList<Spacecraft> spacecraftsf = new ArrayList<>();
    adapter_fav adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orderd_product,container,false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //delcariing the adapter
        adapter = new adapter_fav(getContext(), spacecrafts);
        lvproduct = (ListView) view.findViewById(R.id.lv_fav);
        getSpacecrafts();
        //returnvalue();
       // save("Milk,Yes,2020/3/13!Pepsi,No,2020/3/15!");
        lvproduct.setAdapter(adapter);
       // sendrequesttosave("5");
        adapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);

    }

  /*  public void sendrequesttosave(String number) {
        requestqueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(Request.Method.GET, url + number, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String s1 = response.toString();
                String out = null;
                try {
                    out = new String(s1.getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println("الرد" + out);
                   // save(out);
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


    public void save(String x ) {
        x =x.substring(0,x.indexOf("!"));
        String splitted[] =x.split(",");



        if(splitted.length==3){
            DBAdapter db = new DBAdapter(getContext());
            db.openDB();
            boolean saved = db.addorderd(splitted[0],splitted[1],splitted[2]);
            if (saved) {
                getSpacecrafts();
                Toast.makeText(getContext(), "يوجد لديك طلب جديد ", Toast.LENGTH_SHORT).show();
            }

        }

        else if(splitted.length==6){
            DBAdapter db = new DBAdapter(getContext());
            db.openDB();

            boolean saved = db.addorderd(splitted[0],splitted[1],splitted[2]);
            boolean saved1 = db.addorderd(splitted[3],splitted[4],splitted[5]);
            if (saved) {
                getSpacecrafts();
                Toast.makeText(getContext(), "يوجد لديك طلب جديد ", Toast.LENGTH_SHORT).show();
            }



        }

        else if(splitted.length==9){
            DBAdapter db = new DBAdapter(getContext());
            db.openDB();
            boolean saved = db.addorderd(splitted[0],splitted[1],splitted[2]);
            boolean saved1 = db.addorderd(splitted[3],splitted[4],splitted[5]);
            boolean saved2 = db.addorderd(splitted[6],splitted[7],splitted[8]);
            if (saved) {
                getSpacecrafts();
                Toast.makeText(getContext(), "يوجد لديك طلب جديد ", Toast.LENGTH_SHORT).show();
            }

        }
        else if (splitted.length==1) {
            Toast.makeText(getContext(), "لا يوجد لديك طلبات  ", Toast.LENGTH_SHORT).show();
        }


    }*/

        public void getSpacecrafts() {
        spacecrafts.clear();
        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        Cursor c = db.retrieve2();
        Spacecraft2 spacecraft;
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String product_name=c.getString(1);
            String product_yes=c.getString(2);
            String product_date=c.getString(3);
            spacecraft = new Spacecraft2();
            spacecraft.setId(id);
            spacecraft.setProduct_name(product_name);
            spacecraft.setProduct_yes_no(product_yes);
            spacecraft.setDate_product(product_date);
            spacecrafts.add(spacecraft);
        }
        lvproduct.setAdapter(adapter);


        db.closeDB();
    }



}

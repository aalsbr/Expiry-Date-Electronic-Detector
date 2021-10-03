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
import com.eded.androidap.listvieweded.mDataBase.DBAdapter;
import com.eded.androidap.listvieweded.mDataObject.Spacecraft;
import com.eded.androidap.listvieweded.mListView.adapter_most;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class progress_fragment extends Fragment {

    ListView lvproduct;
    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();
    adapter_most adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.progress_fragment,container,false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //delcariing the adapter
        adapter = new adapter_most(getContext(), spacecrafts);
        lvproduct = (ListView) view.findViewById(R.id.lvmost);
        getSpacecrafts();
        lvproduct.setAdapter(adapter);
        Collections.sort(spacecrafts,new sortbyprogress());
        adapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);
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
            if(finish!=0){
                progress_product=finish/total_progress;
                progress_product=progress_product*100;
                boolean saved = db.savetotalprsentage(id,""+progress_product+"");}


        }
        db.closeDB();
        getSpacecrafts();


    }

    public class sortbyprogress implements Comparator<Spacecraft> {
        @Override
        public int compare(Spacecraft spacecraft, Spacecraft t1) {
            if (Float.parseFloat(spacecraft.getTotalpresentage()) < Float.parseFloat(t1.getTotalpresentage()))
             {
                return 1;
            }
            else if (Float.parseFloat(spacecraft.getTotalpresentage()) > Float.parseFloat(t1.getTotalpresentage())) {


                return -1;
            }
            else {

                return 0;
            }
        }
    }//sorybydate end
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
            String totalprsentage=c.getString(7);
            spacecraft = new Spacecraft();
            spacecraft.setId(id);
            spacecraft.setName(name);
            spacecraft.setDate(date1);
            spacecraft.setStates(state);
            spacecraft.setFinishnumber(finish2);
            spacecraft.setExpirednumber(epired2);
            spacecraft.setOntimenumber(ontime2);
            spacecraft.setTotalpresentage(totalprsentage);
            spacecrafts.add(spacecraft);
        }
        db.closeDB();
        lvproduct.setAdapter(adapter);
    }
}

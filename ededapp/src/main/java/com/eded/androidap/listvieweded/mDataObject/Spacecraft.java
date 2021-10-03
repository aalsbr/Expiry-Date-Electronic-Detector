package com.eded.androidap.listvieweded.mDataObject;



public class Spacecraft {
    String name;
    String date;
    String states;
    String finishnumber;
    String expirednumber;
    String ontimenumber;
    String totalpresentage;
    String radiovalue;
    int id;

    public Spacecraft() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String setName1(String name) {
        this.name = name;
        return this.name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String setDate1(String date) {
        this.date = date;
        return this.date;
    }

    public int getId() {
        return id;
    }


    public String getStates() {
        return states;

    }

    public String  setStates(String states) {
        this.states = states;
        return this.states;
    }

    public void setId(int id) {
        this.id = id;
    }




    public String getFinishnumber() {
        return finishnumber;
    }

    public String getExpirednumber() {
        return expirednumber;
    }

    public String getOntimenumber() {
        return ontimenumber;
    }

    public void setFinishnumber(String finishnumber) {
        this.finishnumber = finishnumber;
    }

    public void setExpirednumber(String expirednumber) {
        this.expirednumber = expirednumber;
    }

    public void setOntimenumber(String ontimenumber) {
        this.ontimenumber = ontimenumber;
    }
    public void setTotalpresentage(String total){
        this.totalpresentage=total;
    }
    public String getTotalpresentage() {
        return totalpresentage;
    }

    public void setRadiovalue(String radiovalue) {
        this.radiovalue = radiovalue;
    }

    public String getRadiovalue() {
        return radiovalue;
    }


}

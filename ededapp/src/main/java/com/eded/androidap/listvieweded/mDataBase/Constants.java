package com.eded.androidap.listvieweded.mDataBase;


import android.widget.RadioGroup;

public class Constants {
    //COLUMNS
    static final String ROW_ID="id";
    static final String ROW_ID2="id2";
    static final String NAME="name";
    static final String DATE ="date";
    static final String STATE ="state";
    static final String FINISHNUMBER ="finishnumber";
    static final String EPIRENUMBER ="expirednumber";
    static final String ONTIMENUMBER ="ontimenumber";
    static final String TOTALPRSENTAGE ="totalprsentage";
    static final String RADIO_VALUE="radiovalue";
    static final String PRODUCT_NAME="productname";
    static final String PRODUCT_NEW_OLD="productnewold";
    static final String PRODUCT_DATE="productdate";

    //DB INFO
    static final String DB_NAME="PRODUCT_DB";
    static final String TB_NAME="_PROCUT_CUR";
    static final String TB_NAME2="_ORDERDPRODUCT";
    static final int DB_VERSION=4;
    //CREATE TB
    static final String CREATE_TB= "CREATE TABLE " + TB_NAME + " (" +
    ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "+
    NAME + " TEXT NOT NULL, "+
    DATE + " TEXT NOT NULL,"+
    STATE + " TEXT NOT NULL,"+
    FINISHNUMBER + " TEXT NOT NULL,"+ TOTALPRSENTAGE + " TEXT NOT NULL,"+ RADIO_VALUE + " TEXT NOT NULL,"+
    EPIRENUMBER + " TEXT NOT NULL,"+ ONTIMENUMBER + " TEXT NOT NULL);"  ;

    static final String CREATE_TB2= "CREATE TABLE " + TB_NAME2 + " (" +
            ROW_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT,  "+
            PRODUCT_NAME + " TEXT NOT NULL,"+
            PRODUCT_NEW_OLD + " TEXT NOT NULL,"+ PRODUCT_DATE + " TEXT NOT NULL);";

    //DROP TB
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME+"DROP TABLE IF EXISTS "+TB_NAME2;
}

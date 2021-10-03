package com.eded.androidap.listvieweded;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;




public class Myfragment extends DatePickerDialogFragment{



    @Override
    protected void initChild() {
        super.initChild();
        mCancelButton.setTextSize(mCancelButton.getTextSize() + 5);
        mDecideButton.setTextSize(mDecideButton.getTextSize() + 5);
        mDatePicker.setShowCurtain(false);
    }

}

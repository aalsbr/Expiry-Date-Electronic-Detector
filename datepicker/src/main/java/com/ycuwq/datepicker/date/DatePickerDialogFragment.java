package com.ycuwq.datepicker.date;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.ycuwq.datepicker.R;


@Deprecated
public class DatePickerDialogFragment extends DialogFragment {


	protected DatePicker mDatePicker;
	private int mSelectedYear = -1, mSelectedMonth = -1, mSelectedDay = -1;
	private OnDateChooseListener mOnDateChooseListener;
	private boolean mIsShowAnimation = true;
	protected Button mCancelButton, mDecideButton;

	public void setOnDateChooseListener(OnDateChooseListener onDateChooseListener) {
		mOnDateChooseListener = onDateChooseListener;
	}

	public void showAnimation(boolean show) {
		mIsShowAnimation = show;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_date, container);

		mDatePicker = (DatePicker) view.findViewById(R.id.dayPicker_dialog);
		mCancelButton = (Button) view.findViewById(R.id.btn_dialog_date_cancel);
		mDecideButton = (Button) view.findViewById(R.id.btn_dialog_date_decide);
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		mDecideButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnDateChooseListener != null) {
					mOnDateChooseListener.onDateChoose(mDatePicker.getYear(),
							mDatePicker.getMonth(), mDatePicker.getDay());
				}
				dismiss();
			}
		});

		if (mSelectedYear > 0) {
			setSelectedDate();
		}

		initChild();
		return view;
	}

	protected void initChild() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity(), R.style.DatePickerBottomDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.dialog_date);
		dialog.setCanceledOnTouchOutside(true);

		Window window = dialog.getWindow();
		if (window != null) {
			if (mIsShowAnimation) {
				window.getAttributes().windowAnimations = R.style.DatePickerDialogAnim;
			}
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.gravity = Gravity.BOTTOM;
			lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			lp.dimAmount = 0.35f;
			window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}

		return dialog;
	}

	public void setSelectedDate(int year, int month, int day) {
		mSelectedYear = year;
		mSelectedMonth = month;
		mSelectedDay = day;
		setSelectedDate();
	}

	private void setSelectedDate() {
		if (mDatePicker != null) {
			mDatePicker.setDate(mSelectedYear, mSelectedMonth, mSelectedDay, false);
		}
	}

	public interface OnDateChooseListener {
		void onDateChoose(int year, int month, int day);
	}


}

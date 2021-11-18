package com.ubestkid.kidrhymes.widget.dialog;

import android.content.Context;
import android.widget.DatePicker;


import com.ubestkid.kidrhymes.utils.TimeUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2020/3/19.
 */

public class DatePickerDialog {

    android.app.DatePickerDialog datePickerDialog;
    private ICallBack mCallBack;

    public interface ICallBack {
        void onResult(String time, long stamp);
    }

    public DatePickerDialog(Context mContext, ICallBack callBack) {
        this.mCallBack = callBack;
        init(mContext);
    }

    private void init(Context mContext) {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);
//        calendar.set();
        datePickerDialog = new android.app.DatePickerDialog(mContext, 3, new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time = year + "-" + (month + 1) + "-" + dayOfMonth;
                Date date = TimeUtil.getDateByFormat(time, TimeUtil.dateFormatYMD3);
                long stamp = 0;
                if (date != null){
                    stamp = date.getTime();
                }
                if (mCallBack != null){
                    mCallBack.onResult(time, stamp);
                }
            }
        }, mYear, mMonth, mDay);

    }

    public void show() {
        datePickerDialog.show();
    }
}

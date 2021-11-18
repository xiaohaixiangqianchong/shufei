package com.ubestkid.kidrhymes.common.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ubestkid.kidrhymes.R;
import com.ubestkid.kidrhymes.common.ioc.IocContainer;


/***
 * IDialog 基本实现
 *
 * @author duohuo-jinghao
 *
 */
public class DialogImpl implements IDialog {

    public Dialog showDialog(Context context, String title, String msg, final DialogCallBack dialogCallBack) {

        return null;
    }

    public Dialog showItemDialog(Context context, String title, CharSequence[] items, final DialogCallBack callback) {
        // new AlertDialog.Builder(context)

        return null;

    }

    class DialogOnItemClickListener implements OnItemClickListener {
        Dialog dialog;

        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        }

    }

    public Dialog showProgressDialog(Context context, String title, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.show();
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    public Dialog showProgressDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.show();
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    public Dialog showProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    public void showToastLong(Context context, String msg) {
        // 使用同一个toast避免 toast重复显示
        Toast toast = IocContainer.getShare().get(Toast.class);
        toast.setDuration(Toast.LENGTH_LONG);
        TextView textView = new TextView(context);
        textView.setText(msg);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(15, 10, 15, 10);
        textView.setBackgroundResource(R.drawable.toast_frame);
        toast.setView(textView);
        toast.show();
    }

    public void showToastShort(Context context, String msg) {
        // 使用同一个toast避免 toast重复显示
        Toast toast = IocContainer.getShare().get(Toast.class);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView textView = new TextView(context);
        textView.setText(msg);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(15, 10, 15, 10);
        textView.setBackgroundResource(R.drawable.toast_frame);
        toast.setView(textView);
        toast.show();
    }

    public void showToastType(Context context, String msg, String type) {
        showToastLong(context, msg);
    }

    public Dialog showDialog(Context context, int icon, String title, String msg, DialogCallBack callback) {
        return showDialog(context, title, msg, callback);
    }

    public Dialog showAdapterDialoge(Context context, String title, ListAdapter adapter,
                                     OnItemClickListener itemClickListener) {

        return null;
    }

    @Override
    public Dialog showErrorDialog(Context context, String title, String msg, DialogCallBack callback) {
        // TODO Auto-generated method stub
        return null;
    }
}

package com.ubestkid.kidrhymes.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.ubestkid.kidrhymes.common.ioc.IocContainer;


public class NomalDialog extends DialogImpl {

	@Override
	public Dialog showProgressDialog(Context context, String msg) {
		if (context == null) {
			return null;
		}
		if (context instanceof Activity){
			Activity activity = (Activity)context;
			if (activity.isFinishing()){
				return null;
			}
		}
		LoadingDialogNew dialog = new LoadingDialogNew(context, msg);
		if (!dialog.isShowing()){
			dialog.show();
		}
		// dialog.setCancelable(true);
		return dialog;
	}

	@Override
	public Dialog showProgressDialog(Context context) {
		return showProgressDialog(context, "", "");
	}

	@Override
	public Dialog showDialog(Context context, String title, String msg,
                             DialogCallBack dialogCallBack) {

		return showDialog(context, 0, title, msg, dialogCallBack);
	}

	@Override
	public void showToastLong(Context context, String msg) {

		if (!TextUtils.isEmpty(msg)) {
			Toast toast = IocContainer.getShare().get(Toast.class);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setText(msg);
			toast.show();
		}
	}

	@Override
	public void showToastShort(Context context, String msg) {
		if (!TextUtils.isEmpty(msg)) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//			Toast toast = IocContainer.getShare().get(Toast.class);
//			toast.setDuration(Toast.LENGTH_SHORT);
//			toast.setText(msg);
//			toast.show();
		}
	}

	@Override
	public void showToastType(Context context, String msg, String type) {
		if (!TextUtils.isEmpty(msg)) {
			super.showToastType(context, msg, type);
		}
	}

	@Override
	public Dialog showAdapterDialoge(Context context, String title,
                                     ListAdapter adapter, OnItemClickListener itemClickListener) {
		return super.showAdapterDialoge(context, title, adapter,
				itemClickListener);

	}

	public Dialog showErrorDialog(Context context, String title, String msg,
                                  DialogCallBack callback) {
		// NetErrorDialog dialog = new NetErrorDialog(context, title, msg);
		// dialog.show();

		return null;
	}

}

package com.ubestkid.kidrhymes.widget.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.ubestkid.kidrhymes.R;


/**
 * @author huqinghan/19044311
 * @description $
 * @date 2020/5/18
 */
public class SimpleDialog extends BaseDialogHelper {
    private static final String TAG = "SimpleDialog";

    /** 回调监听 */
    private CallBackListener callBackListener;

    private TextView titleTv;
    private TextView contentTv;


    public SimpleDialog(Activity that, CallBackListener callBackListener) {
        super(that);
        this.callBackListener = callBackListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_simple;
    }

    @Override
    public void initView(View view) {
        setAnimEnable(false);
        titleTv = view.findViewById(R.id.title_tv);
        contentTv = view.findViewById(R.id.content_tv);
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭弹窗
                closeDialog();
            }
        });

        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭弹窗
                closeDialog();
                //回调 关闭消息
                if (callBackListener != null){
                    callBackListener.confirm();
                }
            }
        });
    }

    public void setTitle(String title){
        titleTv.setText(title);
    }

    public void setContent(String content){
        contentTv.setText(content);
    }

    /**
     * 回调监听
     */
    public interface CallBackListener{
        void confirm();
    }
}
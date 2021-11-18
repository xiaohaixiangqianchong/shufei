package com.ubestkid.kidrhymes.net;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.ubestkid.kidrhymes.common.dialog.IDialog;
import com.ubestkid.kidrhymes.common.ioc.IocContainer;
import com.ubestkid.kidrhymes.utils.JSONUtil;
import com.ubestkid.kidrhymes.utils.LogUtils;
import com.ubestkid.kidrhymes.utils.NetworkUtils;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


public abstract class NormalCallBack extends AbsCallback<String> {

    IDialog dialog;

    Context mContext;

    boolean showDialog = true;

    String loadMsg = "加载中...";

    Dialog progressDialog;

    public String fromWhat = "";
    DResponse dResponse = new DResponse();

    public NormalCallBack(Context context) {
        mContext = context;
    }

    public NormalCallBack(Context context, boolean showDialog) {
        mContext = context;
        this.showDialog = showDialog;
    }

    public NormalCallBack(Context context, String msg) {
        mContext = context;
        this.loadMsg = msg;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (request.getTag() == null) {
            request.tag(mContext);
        }
        try {
            dialog = IocContainer.getShare().get(IDialog.class);
            boolean hasNet = NetworkUtils.isNetworkAvailable();
            if (!hasNet) {
                request.getRawCall().cancel();
                dialog.showToastShort(mContext, "网络连接异常,请重试!");
                onErray();
            } else {
                if (showDialog) {
                    progressDialog = dialog.showProgressDialog(mContext, loadMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFinish() {
        super.onFinish();
        closeDialog();
    }

    /**
     * 数据获取后---异步 解析数据
     */
    @Override
    public String convertResponse(Response response) throws Throwable {


//        if (!response.isSuccessful()) {
//            Log.d("duohuo_result", response.request().url() + " " + response.request().method()
//                    + " " + " " + response.code() + " "
//                    + response.body());
//            return null;
//
//        }
        // Log.d("duohuo_result", request.getUrl() + " " + request.getMethod()
        // + " " + request.getParams() + " " + response.body().string()
        // + "");

        String result = response.body().string();

        JSONObject jo = new JSONObject(result);

        String str;
        // Gson gson;

        // if (new TypeToken<T>() {
        // }.getType() instanceof Map) {
        //
        // gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map>() {
        // }.getType(), new JsonDeserializer<Map>() {
        // @Override
        // public Map<String, Object> deserialize(JsonElement json,
        // Type typeOfT, JsonDeserializationContext context)
        // throws JsonParseException {
        //
        // Map<String, Object> treeMap = new HashMap();
        // JsonObject jsonObject = json.getAsJsonObject();
        // Set<Map.Entry<String, JsonElement>> entrySet = jsonObject
        // .entrySet();
        // for (Map.Entry<String, JsonElement> entry : entrySet) {
        // treeMap.put(entry.getKey(), entry.getValue());
        // }
        // return treeMap;
        // }
        // }).create();
        //
        // } else {
        // gson = new Gson();
        // }
        boolean isSuccess = true;
        if (jo.has("status")) {
            isSuccess = (response.code() == 200 && JSONUtil.getInt(jo, "status") == 200) ? true : false;
            LogUtils.e("jo.has(success)--", isSuccess + "");
        }
//        else {
//            isSuccess = false;
//            LogUtils.e("jo.has(success)--",isSuccess+"");
//        }

        if (isSuccess) {
            if (!TextUtils.isEmpty(fromWhat)) {
                str = JSONUtil.getString(jo, fromWhat);
            } else {
                str = result;
            }
        } else {
            str = result;
        }

        // T data = gson.fromJson(str, new TypeToken<T>() {
        // }.getType());

        // T data = (T) JSON.parseObject(str, ((ParameterizedType)
        // getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ]);
//		ObjectMapper mapper = DhUtil.getDefaultObjectMapper();
//
//		JavaType type = mapper.getTypeFactory().constructType(
//				((ParameterizedType) getClass().getGenericSuperclass())
//						.getActualTypeArguments()[0]);
//
//		T data = (T) mapper.readValue(str, type);

        dResponse.result = result;
        dResponse.data = jo;
        dResponse.msg = JSONUtil.getString(jo, "message");
        dResponse.resultArray = JSONUtil.getJSONArray(jo, "result");
        dResponse.success = isSuccess;
        LogUtils.e("dResponse.success--", dResponse.success + "");
        response.close();
        return result;
    }


    @Override
    public void onError(com.lzy.okgo.model.Response<String> response) {
        super.onError(response);
        closeDialog();

        boolean isShowToast = true;
        try {
            String result = response.getRawResponse().body().string();
            JSONObject jo = new JSONObject(result);
            if (jo.has("msg") && !TextUtils.isEmpty(jo.optString("msg"))) {
                dialog.showToastShort(mContext, jo.optString("msg"));
                isShowToast = false;
            }
        } catch (Exception e) {
        }

        //沒提示過 則彈出默認提示
        if (isShowToast) {
            dialog.showToastShort(mContext, "网络请求异常");
        }

//		if (e instanceof UnknownHostException) {
//			dialog.showToastShort(mContext, "域名不对可能是没有配置网络权限");
//		} else if (e instanceof java.net.SocketTimeoutException) {
//			dialog.showToastShort(mContext, "网络超时");
//		} else {
//			dialog.showToastShort(mContext, e.toString());
//		}
        onErray();
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
        closeDialog();

        if (!dResponse.isSuccess()) {

//            JSONObject data = null;
//            try {
//                data = new JSONObject(dResponse.result);
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

            dialog.showToastShort(mContext, dResponse.msg);

//			onFailUI(dResponse, response.getRawCall());
//			return;
        }
        // TODO Auto-generated method stub
        onSuccessUI(dResponse, response.getRawCall());

    }

    private void closeDialog() {
        try {
            if ((progressDialog != null) && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            progressDialog = null;
        }
    }


    public void onErray() {
    }

    public abstract void onSuccessUI(DResponse response, Call call);

    public void onFailUI(DResponse response, Call call) {
    }

}

package com.ubestkid.kidrhymes.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.ubestkid.kidrhymes.utils.JSONUtil;
import com.ubestkid.kidrhymes.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class DResponse implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public boolean success;
    public String msg;
    public String code;
    public Object data;

    public Object resultArray;

    public String result;

    public boolean cache;

    public boolean isSuccess() {
        LogUtils.e(".isSuccess--",success+"");
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getJSONResult() {
        return (JSONObject) data;
    }

    public String getDataToStr() {
        return JSONUtil.getStringNoEmpty((JSONObject) data, "result");
    }

    public JSONObject getData() {
        return JSONUtil.getJSONObject((JSONObject) data, "data");
    }

    public JSONArray getList() {
        return JSONUtil.getJSONArray((JSONObject) data, "result");
    }

    public JSONArray getResultArray() {
        return (JSONArray)resultArray;
    }


    public void setData(JSONObject data) {
        this.data = data;
    }


    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public <T> List<T> getList(Class<T> clazz) {
        try {
            return JSON.parseArray(getList().toString(), clazz);
        } catch (Exception e) {

        }
        return Arrays.asList();
    }

    public <T> List<T> getResultArray(Class<T> clazz) {
        try {
            return JSON.parseArray(getResultArray().toString(), clazz);
        } catch (Exception e) {

        }
        return Arrays.asList();
    }


    //处理分页
    public <T> List<T> getPageList(Class<T> clazz) {
        try {
            return JSON.parseArray(getData().optJSONArray("data").toString(), clazz);
        } catch (Exception e) {
        }
        return Arrays.asList();
    }

    public <T> T getData(Class<T> clazz) {
        try {
            return JSON.parseObject(getData().toString(), clazz, new Feature[0]);
        } catch (Exception e) {

        }
       return null;
    }

    public JSONObject jSONFrom(String prefix) {
        if (result != null) {
            try {
                return JSONUtil.getJSONObject(new JSONObject(result), prefix);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public JSONObject jSON() {
        if (result != null) {
            try {
                return new JSONObject(result);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}

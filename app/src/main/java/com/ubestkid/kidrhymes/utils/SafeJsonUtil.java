package com.ubestkid.kidrhymes.utils;

import android.graphics.Color;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者： zexu on 2016/11/10 11:00
 * 兼容FastJson，和原生的JSONObject
 */
public class SafeJsonUtil {
/*
    *//**
     * 原生JSONObject
     * @param jo
     * @param key
     * @return
     *//*
    public static boolean getBoolean(JSONObject jo, String key) {
        if (jo == null || key == null) {
            return false;
        }
        if (jo.has(key)) {
            try {
                return jo.optBoolean(key, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    *//**
     * 原生JSONObject
     * @param jo
     * @param key
     * @return
     *//*
    public static String getString(JSONObject jo, String key) {
        if (jo == null || key == null) {
            return "";
        }
        if (jo.has(key)) {
            return jo.optString(key, "");
        }
        return "";

    }

    *//**
     * 原生JSONObject
     * @param json
     * @param key
     * @return
     *//*
    public static int getInteger(JSONObject jo, String key) {
        if (jo == null || key == null) {
            return 0;
        }

        if (jo.has(key)) {
            try {
                return jo.optInt(key, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }*/
    public static int getInteger(String key) {
        if (TextUtils.isEmpty(key)) {
            return 0;
        }
        try {
            return Integer.parseInt(key);
        } catch (Exception e) {
        }
        return 0;
    }

    public static int getInteger(String key, int defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(key);
        } catch (Exception e) {
        }
        return defaultValue;
    }


    public static int getInteger(JSON json, String key) {
        if (json == null || key == null) {
            return 0;
        }

        String value = getString(json, key);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public static int getInteger(JSON json, String key, int defaultValue) {
        if (json == null || key == null) {
            return 0;
        }

        String value = getString(json, key);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
            }
        }
        return defaultValue;
    }

    public static Long getLong(JSON json, String key) {
        String value = getString(json, key);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Long.parseLong(value);
            } catch (Exception e) {
            }

        }
        return 0l;
    }

    public static Float getFloat(JSON json, String key) {
        String value = getString(json, key);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Float.parseFloat(value);
            } catch (Exception e) {
            }
        }
        return 0f;
    }

    public static Float getFloat( String key) {
        if (TextUtils.isEmpty(key)) {
            return 0f;
        }
        try {
            return Float.parseFloat(key);
        } catch (Exception e) {
        }
        return 0f;
    }

    public static Double getDouble(JSON json, String key) {
        String value = getString(json, key);
        if (!TextUtils.isEmpty(value)) {
            try {
                return Double.parseDouble(value);
            } catch (Exception e) {
            }

        }
        return 0d;
    }

    public static Double getDouble( String key) {
        if (!TextUtils.isEmpty(key)) {
            try {
                return Double.parseDouble(key);
            } catch (Exception e) {
            }

        }
        return 0d;
    }


    public static boolean getBoolean( String value) {
        if (!TextUtils.isEmpty(value)) {
            try {
                if("1".equals(value)){
                    return true;
                }else{
                    return Boolean.parseBoolean(value);
                }

            } catch (Exception e) {

            }
        }
        return false;
    }


    public static boolean getBoolean(JSON json, String key) {
        String value = getString(json, key);
        if (!TextUtils.isEmpty(value)) {
            try {
                if("1".equals(value)){
                    return true;
                }else{
                    return Boolean.parseBoolean(value);
                }

            } catch (Exception e) {

            }
        }
        return false;
    }


    public static String getString(JSON json, String key) {
        if (json == null || key == null) {
            return "";
        }
        try {
            String[] tks = key.split("\\.");
            String value = null;
            for (int j = 0; j < tks.length; j++) {
                String tk = tks[j];
                if (j == tks.length - 1) {  //最后一个可以直接取string
                    if (isNum(tk) && !isJSONObjec(json)) {  //只有key是int 类型的并且是JsonArray 才作为array处理
                        JSONArray array = (JSONArray) json;
                        int index = Integer.parseInt(tk);
                        if (index < array.size()) {
                            return array.getString(index);
                        }
                        return "";
                    } else {
                        JSONObject object = (JSONObject) json;
                        if (object.containsKey(tk)) {
                            String finalS = object.getString(tk);
                            if (finalS != null) {
                                return finalS;
                            }
                        }
                        return "";
                    }
                } else {
                    String secondKey = tks[j + 1];
                    json = getJSON(json, tk, secondKey);
                    if (json == null) {
                        return "";
                    }
                }
            }
            if ("null".equals(value)) {
                value = "";
            }
            return value;
        } catch (Exception e) {
        }
        return "";
    }

    public static boolean isNum(String input) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


    public static boolean isJSONObjec(JSON json) {
        if (json instanceof JSONObject) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param json
     * @param firstKey
     * @return
     */
    private static JSON getJSON(JSON json, String firstKey, String secondKey) {
        if (isNum(secondKey)) {
            try {
                if (isJSONObjec(json)) {
                    return ((JSONObject) json).getJSONArray(firstKey);
                } else {
                    return ((JSONArray) json).getJSONArray(Integer.parseInt(firstKey));
                }
            } catch (Exception e) {
            }
        }
        try {
            if (isJSONObjec(json)) {
                return ((JSONObject) json).getJSONObject(firstKey);
            } else {
                return ((JSONArray) json).getJSONObject(Integer.parseInt(firstKey));
            }
        } catch (Exception e) {
        }
        return null;
    }


    public static JSONObject getJSONObject(JSON json, String key) {

        if (json == null || key == null) {
            return new JSONObject();
        }
        try {
            JSONObject jsonObject = null;
            if(isJSONObjec(json)){
                jsonObject = ((JSONObject)json).getJSONObject(key);
            }else{
                jsonObject = ((JSONArray)json).getJSONObject(Integer.parseInt(key));
            }
            if(jsonObject==null){
                jsonObject = new JSONObject();
            }
            return  jsonObject;

        }catch (Exception e){
        }
        return new JSONObject();
    }

    public static JSONArray getJSONArray(JSON json, String key) {
        if (json == null || key == null) {
            return new JSONArray();
        }
        try {
            try {
                if (isJSONObjec(json)) {

                    return ((JSONObject) json).getJSONArray(key) == null ? new JSONArray() : ((JSONObject) json).getJSONArray(key);
                } else {
                    return ((JSONArray) json).getJSONArray(Integer.parseInt(key)) == null ? new JSONArray() : ((JSONObject) json).getJSONArray(key);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        return new JSONArray();
    }

    public static JSONArray getArrayFromArray(JSONArray array, int index) {
        if (array == null || index < 0) {
            return new JSONArray();
        }
        try {
            return array.getJSONArray(index);
        } catch (Exception e) {
        }
        return new JSONArray();
    }

    public static JSONObject getJSONObjectFromArray(JSONArray array, int index) {
        if (array == null || index < 0) {
            return new JSONObject();
        }
        try {
            return array.getJSONObject(index);
        } catch (Exception e) {
        }
        return new JSONObject();
    }


    public static JSONObject parseJSONObject(String text) {
        try {
            return JSON.parseObject(text);
        } catch (Exception e) {
        }

        return new JSONObject();
    }

    public static JSONArray parseJSONArray(String text) {
        try {
            return JSON.parseArray(text);
        } catch (Exception e) {
        }

        return new JSONArray();
    }


    public static <T> T parseBean(JSON json, Class<T> clazz) {
        return parseBean(json.toJSONString(), clazz);

    }


    public static <T> T parseBean(String text, Class<T> clazz) {
        try {
            return JSON.parseObject(text, clazz);
        } catch (Exception e) {
            try {
                return clazz.newInstance();
            } catch (Exception ee) {
            } finally {
                return null;
            }
        }
    }

    public static <T> List<T> parseList(JSON json, Class<T> clazz) {
        return parseList(json.toJSONString(), clazz);
    }

    public static <T> List<T> parseList(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz);
        } catch (Exception e) {

        }
        return Arrays.asList();
    }

    public static int parseColor(String color, String defaultColor){
        try {
            return Color.parseColor(color);
        }catch (Exception e){
            return Color.parseColor(defaultColor);
        }
    }

    public static int parseColor(String color){
       return parseColor(color, "#FE0000");
    }





}

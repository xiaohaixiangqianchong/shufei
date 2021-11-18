package com.ubestkid.kidrhymes.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * json 工具
 * 
 * @author duohuo-jinghao
 * 
 */
public class JSONUtil {

	/**
	 * 获取 string, key可用点用分割 ,空返回null
	 * 
	 * @param jo
	 * @param key
	 * @return
	 */
	public static String getString(JSONObject jo, String key) {
		if (jo == null) {
			return null;
		}
		
		if(TextUtils.isEmpty("key")){
			Log.e("duohuo", "请输入key");
			return null;
		}
		try {
			if (key.contains(".")) {
				String[] tks = key.split("\\."); // 杩欓噷瑕佽浆涔?
				JSONObject jot = jo;
				String value = null;
				for (int j = 0; j < tks.length; j++) {
					String tk = tks[j];
					if (jot.has(tk)) {
						if (j == tks.length - 1) {
							value = jot.getString(tk);
						} else {
							jot = jot.getJSONObject(tk);
						}
					} else {
						return value;
					}
				}
				if ("null".equals(value)) {
					value = "";
				}
				return value;
			} else {
				String value = null;
				if (jo.has(key)) {
					value = jo.getString(key);
				}
				if ("null".equals(value)) {
					value = "";
				}
				return value;
			}
		} catch (JSONException e) {
		}
		return null;
	}

	/**
	 * 获取string key可用点做分割 空时返回""
	 * 
	 * @param jo
	 * @param key
	 * @return
	 */
	public static String getStringNoEmpty(JSONObject jo, String key) {
		String value = getString(jo, key);
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	public static String getString(JSONObject jo, String key, String def) {
		String value = getString(jo, key);
		if (value != null) {
			return value;
		} else {
			return def;
		}
	}

	public static Integer getInt(JSONObject jo, String key) {
		String value = getString(jo, key);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static Integer getInt(JSONObject jo, String key, Integer def) {
		String value = getString(jo, key);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
			}

		}
		return def;
	}

	public static Long getLong(JSONObject jo, String key) {
		String value = getString(jo, key);
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
			}

		}
		return -1l;
	}

	public static Long getLong(JSONObject jo, String key, Long def) {
		String value = getString(jo, key);
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
			}
		}
		return def;
	}

	public static Float getFloat(JSONObject jo, String key) {
		String value = getString(jo, key);
		if (value != null) {
			try {
				return Float.parseFloat(value);
			} catch (Exception e) {
			}
		}
		return 0f;
	}

	public static Double getDouble(JSONObject jo, String key) {
		String value = getString(jo, key);
		if (value != null) {
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
			}

		}
		return 0d;
	}

	/**
	 * 获取boolean 如果没有返回 false
	 * 
	 * @param jo
	 * @param key
	 * @return
	 */
	public static Boolean getBoolean(JSONObject jo, String key) {
		String value = getString(jo, key);
		if (value != null) {
			try {
				return Boolean.parseBoolean(value);
			} catch (Exception e) {
			}
		}
		return false;
	}

	/**
	 * 获取jsonobject key 不可点分割
	 * 
	 * @param jo
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(JSONObject jo, String key) {
		if (jo == null || TextUtils.isEmpty(key))
			return null;
		try {
			if (key.contains(".")) {
				String[] tks = key.split("\\.");
				JSONObject jot = jo;
				JSONObject value = null;
				for (int j = 0; j < tks.length; j++) {
					String tk = tks[j];
					if (jot.has(tk)) {
						if (j == tks.length - 1) {
							value = jot.getJSONObject(tk);
						} else {
							jot = jot.getJSONObject(tk);
						}
					} else {
						return null;
					}
				}
				return value;
			} else {
				JSONObject value = null;
				if (jo.has(key)) {
					value = jo.getJSONObject(key);
				}
				if ("null".equals(value)) {
					value = null;
				}
				return value;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取jsonarray 可不可点分割
	 * 
	 * @param jo
	 * @param key
	 * @return
	 */
	public static JSONArray getJSONArray(JSONObject jo, String key) {
		if (jo == null || TextUtils.isEmpty(key))
			return null;
		try {
			if (key.contains(".")) {
				String[] tks = key.split("\\.");
				JSONObject jot = jo;
				JSONArray value = null;
				for (int j = 0; j < tks.length; j++) {
					String tk = tks[j];
					if (jot.has(tk)) {
						if (j == tks.length - 1) {
							value = jot.getJSONArray(tk);
						} else {
							jot = jot.getJSONObject(tk);
						}
					} else {
						return null;
					}
				}
				return value;
			} else {
				JSONArray value = null;
				if (jo.has(key)) {

					value = jo.getJSONArray(key);
				}
				if ("null".equals(value)) {
					value = null;
				}
				return value;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static JSONObject getJSONObjectAt(JSONArray array, int index) {
		try {
			return array.getJSONObject(index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * json转为map
	 * 
	 * @param jo
	 * @return
	 */
	public static Map<String, Object> jsonToMap(JSONObject jo) {
		if (jo == null)
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		for (@SuppressWarnings("unchecked")
             Iterator<String> iterator = jo.keys(); iterator.hasNext();) {
			String key = (String) iterator.next();
			try {
				Object value = jo.get(key);
				if (!(value instanceof JSONObject)
						&& !(value instanceof JSONArray)) {
					map.put(key, value);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static <T> T modelFrom(JSONObject jo, Class<T> clazz, String prefix) {
		String str = JSONUtil.getString(jo, prefix);
		Gson gson = new Gson();
		T obj = gson.fromJson(str, clazz);
		return obj;
	}

}

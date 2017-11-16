package org.stenerud.kscrash.utils;

import android.text.TextUtils;

import com.pwrd.google.gson.Gson;
import com.pwrd.google.gson.GsonBuilder;
import com.pwrd.google.gson.JsonObject;
import com.pwrd.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * gson解析的工具类
 * @author Jack
 */
public class GsonUtils {

    private static Gson sGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();



    /**
     * json字符串转vo实体对象
     *
     * @param content
     * @param type
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getResult(String content, TypeToken<T> type) {
        T result = null;
        if (!TextUtils.isEmpty(content)) {
            try {
                result = sGson.fromJson(content, type.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * vo对象转成json字符串
     *
     * @param t
     * @param type
     * @param <T>
     * @return
     */
    public static <T> String toJson(T t, Type type) {
        String content = null;
        if (t != null) {
            try {
                content = sGson.toJson(t, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /**
     * map转成JsonObject
     *
     * @param map
     * @return
     */
    public static JsonObject map2Json(Map<String, String> map) {
        JsonObject result = new JsonObject();
        if (map != null && map.size() > 0) {
            for (String key : map.keySet()) {
                result.addProperty(key, map.get(key));
            }
        }
        return result;
    }

    /**
     * hashmap转json   hint应该为对应的jsonobject或jsonarray
     * @param map
     * @return
     */
    public static String hashMapToJson(HashMap map) {
        /*StringBuilder stringBuilder = new StringBuilder("{");
        try {
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                stringBuilder.append("\"").append(key).append("\"").append(":")
                        .append("\"").append(val).append("\"").append(",");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("}");

        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();*/

        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    /**
     * json转list
     * @param json
     * @param token
     * @param <T>
     * @return
     */
    public static <T> List<T> convertList(String json, TypeToken<List<T>> token) {
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<T>();
        }
        return sGson.fromJson(json, token.getType());
    }

    /**
     * 把list转成jsonarray的字符串
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String convertJson(List<T> list){
        String result = "";
        if(list != null){
            result = sGson.toJson(list);
        }
        return result;
    }

}
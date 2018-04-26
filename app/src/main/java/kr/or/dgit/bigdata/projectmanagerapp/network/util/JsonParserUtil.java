package kr.or.dgit.bigdata.projectmanagerapp.network.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ghddb on 2018-04-26.
 */

public  abstract class JsonParserUtil<T> {
    private final String TAG = "Json_parser";

    public  ArrayList<T> parsingJsonArray(String json) {
        ArrayList<T> arItem = new ArrayList<T>();
        T itemClass;
        try {
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject order = ja.getJSONObject(i);

                itemClass = itemParse(order);
                Log.d(TAG,itemClass.toString());
                arItem.add(itemClass);
            }
        } catch (JSONException e) {
            Log.i("Json_parser", e.getMessage());
        }
        return arItem;
    }

    public  T parsingJson(String json) {
        T itemClass = null;
        try {
                JSONObject order = new JSONObject(json);

                itemClass = itemParse(order);
                Log.d(TAG,itemClass.toString());

        } catch (JSONException e) {
            Log.i("Json_parser", e.getMessage());
        }
        return itemClass;
    }

    public  abstract T itemParse(JSONObject order) throws  JSONException;

}


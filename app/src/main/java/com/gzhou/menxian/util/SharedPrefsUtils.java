package com.gzhou.menxian.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefsUtils {
    private static SharedPreferences sharedPreferences;
    private static Context _context;
    private static Boolean[] contextLock = new Boolean[0];
    private static Boolean[] preferenceLock = new Boolean[0];
    private static final String MY_PREF = "MY_PREF";
    private static final String RESTAURANT_IDS = "restaurants_ids";


    private static Context getAppContext(Context context) {
        if (_context == null) {
            synchronized (contextLock) {
                if (_context == null) {
                    _context = context;
                }
            }
        }
        return _context;
    }
    private static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            synchronized (preferenceLock) {
                sharedPreferences = getAppContext(context).getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
            }
        }
        return sharedPreferences;
    }

    public static ArrayList<Integer> getDatas(Context context) {
        String datastring = getSharedPreferences(context).getString(RESTAURANT_IDS, null);
        ArrayList<Integer> idist;
        Type listType = new TypeToken<ArrayList<Integer>>() { // object can be String here
        }.getType();
        String previousHistory = datastring;
        idist = new Gson().fromJson(previousHistory, listType);
        return idist;
    }

    public static void putData(Context context, Integer id) {
        List<Integer> idList = getDatas(context);
        if (idList == null) {
            idList = new ArrayList<>();
        }
        if (!idList.contains(id)) {
            idList.add(id);
        }
        String dataString = Utils.getGson().toJson(idList);
        getSharedPreferences(context).edit().putString(RESTAURANT_IDS, dataString).apply();
    }
}

package com.gzhou.menxian.util;

import android.content.Context;

import com.gzhou.menxian.models.RestaurantData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataLab {
    private static DataLab dataLab;

    private ArrayList<Integer> idList;

    public static DataLab get(Context context) {
        if (dataLab == null) {
            dataLab = new DataLab(context);
        }
        return dataLab;
    }

    private DataLab(Context context) {
        idList = SharedPrefsUtils.getDatas(context);
    }

    public List<Integer> getDatas(Context context) {
        return SharedPrefsUtils.getDatas(context);
    }

    public void putData(Context context, Integer id) {
        SharedPrefsUtils.putData(context, id);
    }
    public Integer getData(int idx) {
        if (idx < idList.size() && idx > 0) {
            return idList.get(idx);
        }
        return null;
    }
}

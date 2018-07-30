package com.gzhou.menxian.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    public static Gson getGson() {
        return GSON;
    }

    public static void launchActivity(final Context currentContext, final Class classOfActivityToLaunch, final Bundle bundle) {
        Intent activityToLaunch = new Intent(currentContext, classOfActivityToLaunch);
        if (bundle != null) {
            activityToLaunch.putExtras(bundle);
        }
        currentContext.startActivity(activityToLaunch);
    }
}

package com.example.qtandroid;

import android.content.Context;
import android.content.Intent;

public final class ActivityUtils {

    private ActivityUtils() {
    }

    static void open(Class<?> activity, Context context) {
        Intent i = new Intent(context, activity);
        context.startActivity(i);
    }
}

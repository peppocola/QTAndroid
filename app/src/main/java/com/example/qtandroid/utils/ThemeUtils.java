package com.example.qtandroid.utils;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.qtandroid.R;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public final class ThemeUtils {

    private ThemeUtils() {
    }

    public static int defaultTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            return (R.style.DarkMode);
        } else {
            return (R.style.AppTheme);
        }
    }

    public static void listen(final Context context, Switch DarkSwitch) {
        DarkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
                    ((AppCompatActivity) context).recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
                    ((AppCompatActivity) context).recreate();
                }
            }
        });
    }

}

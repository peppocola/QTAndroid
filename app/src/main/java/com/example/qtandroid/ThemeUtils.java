package com.example.qtandroid;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtils {


    public static int defaultTheme() {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            return(R.style.DarkMode);
        }
        else return(R.style.AppTheme);
    }

}

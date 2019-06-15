package com.example.qtandroid;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {

    public static void openDetails(Context context) {
        ActivityUtils.open(Details.class, context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(ThemeUtils.defaultTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

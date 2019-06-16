package com.example.qtandroid;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Contacts extends AppCompatActivity {

    public static void openContacts(Context context) {
        ActivityUtils.open(FileCluster.class, context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }
}

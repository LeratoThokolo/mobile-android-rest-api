package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thokolo.lerato.draweractivitywithfragments.R;

public class ActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");

    }
}

package com.example.sober_philer.transparencyfromwindows;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.philer.TransHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TransHelper(MainActivity.this).transContent(getResources().getColor(R.color.statusBarColor));
    }
}

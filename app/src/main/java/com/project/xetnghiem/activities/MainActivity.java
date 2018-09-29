package com.project.xetnghiem.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.xetnghiem.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public String getMainTitle() {
        return "Main ";
    }

    @Override
    public void updateUIData(Object obj) {

    }
}

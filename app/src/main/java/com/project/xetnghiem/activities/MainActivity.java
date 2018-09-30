package com.project.xetnghiem.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.xetnghiem.R;

public class MainActivity extends BaseActivity {
    private View gridBookAppt;
    private View gridAccount;
    private View gridTreatHistory;
    private View gridApptHistory;
    private View gridResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.side_nav_bar));
        }
    }

    @Override
    public String getMainTitle() {
        return "Trang chủ ";
    }

    @Override
    public void bindView() {
        gridBookAppt = findViewById(R.id.grid_book_appt);
        gridAccount = findViewById(R.id.grid_account);
        gridTreatHistory = findViewById(R.id.grid_treatment_history);
        gridApptHistory = findViewById(R.id.grid_appt_history);
        gridResult = findViewById(R.id.grid_result);

        gridBookAppt.setOnClickListener((v)->{
            redirectToActivity(BookApptActivity.class, false);
        });
        gridAccount.setOnClickListener((v)->{
            redirectToActivity(AccountActivity.class, false);

        });
        gridTreatHistory.setOnClickListener((v)->{
            redirectToActivity(BookApptActivity.class, false);});
        gridApptHistory.setOnClickListener((v)->{
            redirectToActivity(BookApptActivity.class, false);});
        gridResult.setOnClickListener((v)->{
            redirectToActivity(BookApptActivity.class, false);});
    }

    @Override
    public void updateUIData(Object obj) {

    }
}

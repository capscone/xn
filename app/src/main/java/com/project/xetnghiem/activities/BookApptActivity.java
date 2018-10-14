package com.project.xetnghiem.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.CustomViewPager;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.requestObj.AppointmentRequest;
import com.project.xetnghiem.api.responseObj.SuccessResponse;
import com.project.xetnghiem.api.services.SampleService;
import com.project.xetnghiem.api.services.UserService;
import com.project.xetnghiem.fragment.BookStep1Fragment;
import com.project.xetnghiem.fragment.BookStep2Fragment;
import com.project.xetnghiem.fragment.NewAppointmentFragment;
import com.project.xetnghiem.fragment.OldAppointmentFragment;
import com.project.xetnghiem.models.LabTest;
import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;
import com.project.xetnghiem.utilities.Validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BookApptActivity extends BaseActivity implements BookStep1Fragment.DataListener {

    private Disposable appointmentDisposable;
    //    private User user;
//    private Patient patient;
    private boolean isDateValid = true;
    private CustomViewPager viewPager;
    private List<LabTest> tmpLabTest;
    private List<SampleDto> listTmpSampleDto;
private  Button btnNextStep;
    @Override
    protected int getLayoutView() {
        return R.layout.activity_quick_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setBackgroundDrawable(
//                    ContextCompat.getDrawable(BookApptActivity.this, R.drawable.side_nav_bar)
//            );
//        }
//        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        String mPhoneNumber = tMgr.getLine1Number();
//        tvPhone.setText(mPhoneNumber);
//        img.requestFocus();
//        tvFullname.clearFocus();

        tmpLabTest = new ArrayList<>();
        listTmpSampleDto = new ArrayList<>();

    }

    @Override
    public String getMainTitle() {
        return "Đặt lịch";
    }

    @Override
    public void bindView() {
        viewPager = findViewById(R.id.pager_sample);
        btnNextStep = findViewById(R.id.btn_next_step);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BookStep1Fragment(), "Lịch hẹn mới");
        adapter.addFrag(new BookStep2Fragment(), "Lịch hẹn cũ");
        viewPager.setAdapter(adapter);
        viewPager.setEnabled(false);
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }



    @Override
    public void callDataResource() {

    }

    @Override
    public void updateUIData(Object obj) {

    }

    private Disposable apiDisposable;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onCancelLoading() {
        if (appointmentDisposable != null) {
            appointmentDisposable.dispose();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onDateReceiver(List<LabTest> data,List<SampleDto> listTmpSampleDto) {
        tmpLabTest.clear();
        tmpLabTest.addAll(data);
        this.listTmpSampleDto.clear();
        this.listTmpSampleDto.addAll(listTmpSampleDto);
        int a = 1;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

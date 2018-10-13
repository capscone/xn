package com.project.xetnghiem.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.xetnghiem.R;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.requestObj.AppointmentRequest;
import com.project.xetnghiem.api.responseObj.SuccessResponse;
import com.project.xetnghiem.api.services.SampleService;
import com.project.xetnghiem.api.services.UserService;
import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.utilities.CoreManager;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;
import com.project.xetnghiem.utilities.Validation;

import java.util.Calendar;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BookApptActivity extends BaseActivity {
    private AutoCompleteTextView tvPhone;
    private AutoCompleteTextView tvFullname;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvPrice;
    private TextView tvDateError;
    private AutoCompleteTextView comtvNote;
    private Button btnQuickBook;
    private Disposable appointmentDisposable;
    //    private User user;
//    private Patient patient;
    private boolean isDateValid = true;

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


        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Calendar currentDay = Calendar.getInstance();
        tvDate.setOnClickListener((view) ->
        {
            showMessage("date");
            DatePickerDialog dialog = new DatePickerDialog(this,
                    (DatePicker datePicker, int iYear, int iMonth, int iDay) -> {
                        String date = iDay + "/" + (iMonth + 1) + "/" + iYear;
                        c.set(iYear, iMonth, iDay, 23, 59);
                        if (currentDay.after(c)) {
                            tvDateError.setText(getString(R.string.label_error_appnt_date));
                            isDateValid = false;
                        } else {
                            tvDateError.setText("");
                            isDateValid = true;
                        }
                        tvDate.setText(DateUtils.getDate(c.getTime(), DateTimeFormat.DATE_APP));
                        tvDate.setTextColor(
                                ContextCompat.getColor(BookApptActivity.this, R.color.color_black)
                        );
                    }, year, month, day);
            dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.OK), dialog);
            dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.Cancel), (DialogInterface.OnClickListener) null);

            dialog.show();
        });
        tvTime.setOnClickListener((v) -> {
            showMessage("time");
            TimePickerDialog dialog = new TimePickerDialog(BookApptActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    String time = i + " : " + i1;
                    tvTime.setText(time); tvTime.setTextColor(
                            ContextCompat.getColor(BookApptActivity.this, R.color.color_black)
                    );
                }
            }, currentDay.get(Calendar.HOUR_OF_DAY), currentDay.get(Calendar.MINUTE), true);
            dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.OK), dialog);
            dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.Cancel), (DialogInterface.OnClickListener) null);

            dialog.show();
        });
//        if(CoreManager.getCurrentPatient(BookApptActivity.this)!=null){
//            patient= CoreManager.getCurrentPatient(BookApptActivity.this);
//            if(patient.getPhone()!=null){
//                tvPhone.setText(patient.getPhone());
//            }
//            if(patient.getName()!=null){
//                tvFullname.setText(patient.getName());
//
//            }
//        }

        btnQuickBook.setOnClickListener((view) -> {
            if (isValidateForm()) {
                AppointmentRequest requestObj = getFormData();
                if (requestObj != null) {
                    callApi(requestObj);
                } else {
                    showWarningMessage("Error null");
                }
            }

//            Intent intent = new Intent(BookApptActivity.this, SelectDentistActivity.class);
//            startActivity(intent);
        });

    }

    @Override
    public String getMainTitle() {
        return "Đặt lịch";
    }

    @Override
    public void bindView() {
        tvDate = findViewById(R.id.tv_date_quickbook);
        tvDateError = findViewById(R.id.tv_date_error_quickbook);
        tvTime = findViewById(R.id.tv_time_quickbook);
        tvPrice = findViewById(R.id.tv_price);
        btnQuickBook = findViewById(R.id.btn_quickbook);

    }

    @Override
    public void callDataResource() {
        SampleService sampleService = APIServiceManager.getService(SampleService.class);
        sampleService.getAllSample().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<List<SampleDto>>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        apiDisposable = d;
                    }

                    @Override
                    protected void onResponseSuccess(Response<List<SampleDto>> sampleDtoResponse) {
                        List<SampleDto> lst = sampleDtoResponse.body();
                        int a = 1;
                    }
                });
    }

    @Override
    public void updateUIData(Object obj) {

    }
private Disposable apiDisposable
        ;
    @Override
    protected void onStop() {
        super.onStop();
        if (apiDisposable != null) {
            apiDisposable.dispose();
        }
    }

    @Override
    public void onCancelLoading() {
        if (appointmentDisposable != null) {
            appointmentDisposable.dispose();
        }
    }

    public AppointmentRequest getFormData() {
//        User user = CoreManager.getUser(this);
//        if (user != null) {
//            String phone = user.getPhone();
//        String phone = tvPhone.getText().toString().trim();
        String dateBooking = tvDate.getText().toString().trim();
        String note = comtvNote.getText().toString().trim();
        String name = tvFullname.getText().toString().trim();
        String bookingDate = DateUtils.changeDateFormat(
                dateBooking,
                DateTimeFormat.DATE_APP,
                DateTimeFormat.DATE_TIME_DB);
        AppointmentRequest request = new AppointmentRequest();
        request.setDate(bookingDate);
        request.setNote(note);
        request.setFullname(name);
//        request.setPhone(phone);
        return request;
//        }
//        return null;
    }

    public boolean isValidateForm() {
        boolean isAllFieldValid = true;
//        String phone = tvPhone.getText().toString().trim();
//        String note = comtvNote.getText().toString().trim();
        String txtDate = tvDate.getText().toString().trim();
        View viewFocus = null;
        if (Validation.isNullOrEmpty(txtDate)
                || (txtDate != null && txtDate.equals(getString(R.string.label_date_bookapt)))) {
            viewFocus = tvDateError;
            tvDateError.setText("Vui lòng chọn ngày");
            isAllFieldValid = false;
        }
        if (!isAllFieldValid) {
            viewFocus.requestFocus();
        }
        return isAllFieldValid && isDateValid;
    }

    public void callApi(AppointmentRequest requestObj) {
        showLoading();
        UserService appointmentService =
                APIServiceManager.getService(UserService.class);
//        appointmentService.bookAppointment(requestObj)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Response<SuccessResponse>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        appointmentDisposable = d;
//                    }
//
//                    @Override
//                    public void onSuccess(Response<SuccessResponse> response) {
//                        if (response.isSuccessful()) {
//                            String successMsg = "Đặt lịch thành công";
//                            if (response.body() != null) {
//                                successMsg = response.body().getMessage();
//                            }
//                            AlertDialog.Builder builder = new AlertDialog.Builder(BookApptActivity.this)
//                                    .setTitle(getString(R.string.dialog_default_title))
//                                    .setMessage(successMsg)
//                                    .setPositiveButton("Xác nhận", (DialogInterface var1, int var2) -> {
//                                        finish();
//                                    });
//                            builder.create().show();
//                        } else if (response.code() == 500) {
//                            showFatalError(response.errorBody(), "appointmentService");
//                        } else if (response.code() == 401) {
//                            showErrorUnAuth();
//                        } else if (response.code() == 400) {
//                            showBadRequestError(response.errorBody(), "appointmentService");
//                        } else {
//                            showErrorMessage(getString(R.string.error_on_error_when_call_api));
//                        }
//                        hideLoading();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        showWarningMessage(getResources().getString(R.string.error_on_error_when_call_api));
//                        hideLoading();
//                    }
//                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}

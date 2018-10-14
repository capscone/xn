package com.project.xetnghiem.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.xetnghiem.R;
import com.project.xetnghiem.activities.BookApptActivity;
import com.project.xetnghiem.api.requestObj.AppointmentRequest;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;
import com.project.xetnghiem.utilities.Validation;

import java.util.Calendar;

public class BookStep2Fragment extends BaseFragment {
    private AutoCompleteTextView tvPhone;
    private AutoCompleteTextView tvFullname;
    private boolean isDateValid = true;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvPrice;
    private TextView tvDateError;
    private Button btnQuickBook;View mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        mainView = inflater.inflate(R.layout.fragment_book_step2, container, false);

        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindView();
    }

    @Override
    public void bindView() {

        tvDate = mainView.findViewById(R.id.tv_date_quickbook);
        tvDateError = mainView.findViewById(R.id.tv_date_error_quickbook);
        tvTime = mainView.findViewById(R.id.tv_time_quickbook);
        tvPrice =mainView. findViewById(R.id.tv_price);
        btnQuickBook = mainView.findViewById(R.id.btn_quickbook);


        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Calendar currentDay = Calendar.getInstance();
        tvDate.setOnClickListener((view) ->
        {
            showMessage("date");
            DatePickerDialog dialog = new DatePickerDialog(getContext(),
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
                                ContextCompat.getColor(getContext(), R.color.color_black)
                        );
                    }, year, month, day);
            dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.OK), dialog);
            dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.Cancel), (DialogInterface.OnClickListener) null);

            dialog.show();
        });
        tvTime.setOnClickListener((v) -> {
            showMessage("time");
            TimePickerDialog dialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    String time = i + " : " + i1;
                    tvTime.setText(time); tvTime.setTextColor(
                            ContextCompat.getColor(getContext(), R.color.color_black)
                    );
                }
            }, currentDay.get(Calendar.HOUR_OF_DAY), currentDay.get(Calendar.MINUTE), true);
            dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.OK), dialog);
            dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.Cancel), (DialogInterface.OnClickListener) null);

            dialog.show();
        });

        btnQuickBook.setOnClickListener((view) -> {
        });
    }

    @Override
    public void updateUIData(Object obj) {
        
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

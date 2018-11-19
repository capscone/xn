package com.project.xetnghiem.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.BookSampleAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.requestObj.ApptCreateRequest;
import com.project.xetnghiem.api.responseObj.SuccessResponse;
import com.project.xetnghiem.api.services.BookApptService;
import com.project.xetnghiem.models.LabTest;
import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.models.Slot;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;
import com.project.xetnghiem.utilities.Validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BookStep2Fragment extends BaseFragment {
    private AutoCompleteTextView tvPhone;
    private AutoCompleteTextView tvFullname;
    private boolean isDateValid = true;
    private TextView tvDate;
    private TextView txtTime;
    private TextView txtDate;
    private ListView listSampleBook;
    private TextView tvDateError;
    private Button btnQuickBook;
    View mainView;
    private BookSampleAdapter adapter;
    private List<SampleDto> listSampleDto;
    private List<Slot> availableSlots;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        mainView = inflater.inflate(R.layout.fragment_book_step2, container, false);
        bindView();
        if (listSampleDto == null) {
            listSampleDto = new ArrayList<>();
        }
        if (availableSlots == null) {
            availableSlots = new ArrayList<>();
        }
        if (adapter == null) {
            if (getContext() != null) {

                adapter = new BookSampleAdapter(getContext(), listSampleDto, new BookSampleAdapter.SpinnerSelectLisenter() {
                    @Override
                    public void onClick(Slot data, int sampleId) {
                        SampleDto dto = findInList(sampleId);
                        if (dto != null) {
//                            dto.setTimeStr(data);
                            dto.setSelectedSlotId(data.getSlotId());
                        }
                    }
                });
            }
        }
        listSampleBook.setAdapter(adapter);
        return mainView;
    }

    public SampleDto findInList(int sampleId) {
        for (SampleDto dto : listSampleDto) {
            if (dto.getSampleId() == sampleId) {
                return dto;
            }
        }
        return null;
    }

    public void setDataSample(List<SampleDto> list) {
        listSampleDto.clear();
        listSampleDto.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void setAvailableSlots(List<Slot> list) {
        availableSlots.clear();
        this.availableSlots.addAll(list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void bindView() {

//        tvTime = mainView.findViewById(R.id.tv_time_quickbook);
//        tvPrice =mainView. findViewById(R.id.tv_price);
        btnQuickBook = mainView.findViewById(R.id.btn_book);
        listSampleBook = mainView.findViewById(R.id.list_view_book_sample);
        txtDate = mainView.findViewById(R.id.txt_sample_date);
//        txtTime = mainView.findViewById(R.id.txt_sample_time);

        btnQuickBook.setOnClickListener((view) -> {
            callApiBookAppointment();
        });
        if ( txtDate != null) {
            final TextView txtDateFinal = txtDate;
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            Calendar currentDay = Calendar.getInstance();
              txtDate.setText(DateUtils.getDate(c.getTime(), DateTimeFormat.DATE_APP));
              txtDate.setOnClickListener((vw) ->
            {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        (DatePicker datePicker, int iYear, int iMonth, int iDay) -> {
                            String date = iDay + "/" + (iMonth + 1) + "/" + iYear;
                            c.set(iYear, iMonth, iDay, 23, 59);
//                            if (currentDay.after(c)) {
////                                tvDateError.setText(getString(R.string.label_error_appnt_date));
////                                isDateValid = false;
//                            } else {
////                                tvDateError.setText("");
////                                isDateValid = true;
//                            }
                            txtDateFinal.setText(DateUtils.getDate(c.getTime(), DateTimeFormat.DATE_APP));
//                            holder.txtDate.setTextColor(
//                                    ContextCompat.getColor(getContext(), R.color.color_black)
//                            );
                        }, year, month, day);
//                dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.OK), dialog);
//                dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.Cancel), (DialogInterface.OnClickListener) null);

                dialog.show();
            });
        }

    }

    public void callApiBookAppointment() {
        ApptCreateRequest request = new ApptCreateRequest();
        request.setPatientId(71);

        List<ApptCreateRequest.SampleGettingDtos> list = new ArrayList<>();
        for (SampleDto dto : listSampleDto) {
            ApptCreateRequest.SampleGettingDtos dtos = new ApptCreateRequest.SampleGettingDtos();
            List<Integer> listIdLabTests = new ArrayList<>();
            for (LabTest labTest : dto.getLabTests()) {
                listIdLabTests.add(labTest.getLabTestId());
            }
//            dtos.setStartTime(dateFormat + " " + dtimes[0].trim());
//            dtos.setStartTime(dateFormat + " " + dtimes[1].trim());
            dtos.setLabTestIds(listIdLabTests);
            dtos.setSampleId(dto.getSampleId());
            dtos.setGetttingDate(dto.getDateStr());
            dtos.setSlotId(dto.getSelectedSlotId());
            list.add(dtos);
        }
        request.setList(list);

        showLoading();
        BookApptService service = APIServiceManager.getService(BookApptService.class);
        service.bookAppointment(request).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<SuccessResponse>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    protected void onResponseSuccess(Response<SuccessResponse> response) {
                        if (response.body().isSuccess()) {
                            showMessage("Đặt lịch thành công.");
                            getActivity().finish();
                        }else{
                            showMessage(response.body().getMessage());
                        }
                    }
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

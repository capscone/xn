package com.project.xetnghiem.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.AppointmentHeaderAdapter;
import com.project.xetnghiem.adapter.AppointmentOldHeaderAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.services.AppointmentService;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.utilities.DateTimeFormat;
import com.project.xetnghiem.utilities.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class OldAppointmentFragment extends BaseFragment {

    private ListView listView;
    private List<AppointmentDetail> listAppt;
    private AppointmentOldHeaderAdapter adapter;
    public OldAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_old_appointment, container, false);
        listView = v.findViewById(R.id.rcv_old_appointment);
        if (listAppt == null)
            listAppt = new ArrayList<>();
        if (adapter == null) {
            adapter = new AppointmentOldHeaderAdapter(getContext(), listAppt);
        }
        listView.setAdapter(adapter);
//        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        callDataResource();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void bindView() {

    }

    @Override
    protected void callDataResource() {
        showLoading();
        AppointmentService service = APIServiceManager.getService(AppointmentService.class);
        service.getOldApptByPatientId(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<List<Appointment>>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        apiDisposable = d;
                    }

                    @Override
                    protected void onResponseSuccess(Response<List<Appointment>> appointmentResponse) {
                        if (appointmentResponse.body() != null) {
                            for (Appointment appointment : appointmentResponse.body()) {
                                listAppt.addAll(appointment.getListApptDetail());
                            }
                            Collections.sort(listAppt, new OldAppointmentFragment.AppointmentDetailSort());
                            int i = listAppt.size() - 1;
                            AppointmentDetail prev = listAppt.get(i);
                            AppointmentDetail crr = null;
                            do {
                                i--;
                                String prvFormat =  prev.getGettingDate( );
                                if (i < 0) {
                                    listAppt.add(i + 1, new AppointmentDetail(true, prvFormat));
                                    break;
                                }
                                crr = listAppt.get(i);
                                String crrFormat =  crr.getGettingDate( );
                                if (!prvFormat.equals(crrFormat)) {
                                    listAppt.add(i + 1, new AppointmentDetail(true, prvFormat));
                                }
                                prev = crr;
                            } while (i >= 0);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    private Disposable apiDisposable;
    class AppointmentDetailSort implements Comparator<AppointmentDetail> {

        @Override
        public int compare(AppointmentDetail a1, AppointmentDetail a2) {
            String dateFormat1 = DateUtils.changeDateFormat(a1.getGettingDate(),
                    DateTimeFormat.DATE_TIME_DB_2, DateTimeFormat.DATE_APP_2);
            String dateFormat2 = DateUtils.changeDateFormat(a2.getGettingDate(),
                    DateTimeFormat.DATE_TIME_DB_2, DateTimeFormat.DATE_APP_2);
            if (dateFormat2 != null && dateFormat1 != null) {
                return dateFormat2.compareTo(dateFormat1);
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
    @Override
    public void updateUIData(Object obj) {

    }
}

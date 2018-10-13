package com.project.xetnghiem.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.adapter.AppointmentAdapter;
import com.project.xetnghiem.adapter.AppointmentHeaderAdapter;
import com.project.xetnghiem.api.APIServiceManager;
import com.project.xetnghiem.api.MySingleObserver;
import com.project.xetnghiem.api.services.AppointmentService;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.models.BaseContext;
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

public class NewAppointmentFragment extends BaseFragment implements BaseContext {
    private ListView listView;
    private List<AppointmentDetail> listAppt;
    private AppointmentHeaderAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        listView = v.findViewById(R.id.rcv_new_appointment);
        if (listAppt == null)
            listAppt = new ArrayList<>();
        if (adapter == null) {
            adapter = new AppointmentHeaderAdapter(getContext(), listAppt);
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
        service.getNewApptByPatientId(1)
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
                            Collections.sort(listAppt, new AppointmentDetailSort());
                            int i = listAppt.size() - 1;
                            AppointmentDetail prev = listAppt.get(i);
                            AppointmentDetail crr = null;
                            do {
                                i--;
                                String prvFormat = getDateFormat(prev.getStartTime());
                                if (i < 0) {
                                    listAppt.add(i + 1, new AppointmentDetail(true, prvFormat));
                                    break;
                                }
                                crr = listAppt.get(i);
                                String crrFormat = getDateFormat(crr.getStartTime());
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

    public String getDateFormat(String src) {
        String dateTmp = DateUtils.changeDateFormat(src,
                DateTimeFormat.DATE_TIME_DB_3, DateTimeFormat.DATE_APP_2);
        return dateTmp;
    }

    @Override
    public void updateUIData(Object obj) {
    }

    class AppointmentDetailSort implements Comparator<AppointmentDetail> {

        @Override
        public int compare(AppointmentDetail a1, AppointmentDetail a2) {
            String dateFormat1 = DateUtils.changeDateFormat(a1.getStartTime(),
                    DateTimeFormat.DATE_TIME_DB_3, DateTimeFormat.DATE_APP_2);
            String dateFormat2 = DateUtils.changeDateFormat(a2.getStartTime(),
                    DateTimeFormat.DATE_TIME_DB_3, DateTimeFormat.DATE_APP_2);
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

    private Disposable apiDisposable;

    @Override
    public void onResume() {
        super.onResume();
//        if (apiDisposable != null) {
//            apiDisposable.dispose();
//        }
    }
}

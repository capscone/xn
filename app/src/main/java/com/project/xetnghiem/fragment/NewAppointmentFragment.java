package com.project.xetnghiem.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
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
import com.project.xetnghiem.utilities.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NewAppointmentFragment extends BaseFragment implements BaseContext {
    private SwipeMenuListView listView;
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
        ///test
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {switch (menu.getViewType()) {
                case AppointmentHeaderAdapter.TYPE_SEPARATOR:

                    break;
                case AppointmentHeaderAdapter.TYPE_ITEM:
                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            getContext());
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                            0xCE)));
                    // set item width
                    openItem.setWidth((int)Utils.convertDpToPixel((float)90 , getContext()));
                    // set item title
                    openItem.setTitle("Open");
                    // set item title fontsize
                    openItem.setTitleSize(18);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);

                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getContext());
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    // set item width
                    deleteItem.setWidth((int)Utils.convertDpToPixel((float)90 , getContext()));
                    // set a icon
                    deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                    // create menu of type 1
                    break;
            }

            }
        };

// set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        AppointmentDetail ad =  listAppt.get(position);
                        break;
                    case 1:
                      AppointmentDetail ad2 =  listAppt.get(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        
        //endtest


        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
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

    public String getDateFormat(String src) {
        String dateTmp = DateUtils.changeDateFormat(src,
                DateTimeFormat.DATE_TIME_DB_2, DateTimeFormat.TIME_APP_1);
        return dateTmp;
    }

    @Override
    public void updateUIData(Object obj) {
    }

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

    private Disposable apiDisposable;

    @Override
    public void onResume() {
        super.onResume();
//        if (apiDisposable != null) {
//            apiDisposable.dispose();
//        }
    }
}

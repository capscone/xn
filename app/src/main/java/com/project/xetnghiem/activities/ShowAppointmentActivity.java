package com.project.xetnghiem.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.project.xetnghiem.api.responseObj.ResponseMessage;
import com.project.xetnghiem.api.responseObj.SuccessResponse;
import com.project.xetnghiem.api.services.AppointmentService;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.utilities.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ShowAppointmentActivity extends BaseActivity implements AppointmentAdapter.AppointmentAdapterListener {
//    private SwipeMenuListView swipeMenuListView;
    private ListView listView;
    private AppointmentAdapter adapter;
    private List<Appointment> listAppointment;
    private final int EDIT_INDEX = 0;
    private final int DELETE_INDEX = 1;
    private final int SHOW_INDEX = 2;
    public static final String LIST_LABTEST_ID = "LIST_LABTEST_ID";
    public static final String APPT_ID = "APPT_ID";
    public static final String LIST_APPT_DETAIL = "LIST_APPT_DETAIL";

    @Override
    protected int getLayoutView() {
        return R.layout.activity_show_patient_appt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (listAppointment == null) {
            listAppointment = new ArrayList<>();
        }
        adapter = new AppointmentAdapter(this, listAppointment,this);
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                switch (menu.getViewType()) {
//                    case AppointmentAdapter.TYPE_NEW:
//                        SwipeMenuItem editItem = new SwipeMenuItem(
//                                ShowAppointmentActivity.this);
//                        editItem.setBackground(new ColorDrawable(Color.BLUE));
//                        editItem.setWidth((int) Utils.convertDpToPixel((float) 90, ShowAppointmentActivity.this));
//                        editItem.setTitleSize(18);
//                        editItem.setIcon(R.drawable.ic_edit_black_24dp);
//                        editItem.setId(EDIT_INDEX);
//                        menu.addMenuItem(editItem);
//
//                        SwipeMenuItem deleteItem = new SwipeMenuItem(
//                                ShowAppointmentActivity.this);
//                        deleteItem.setBackground(new ColorDrawable(Color.RED));
//                        deleteItem.setWidth((int) Utils.convertDpToPixel((float) 90, ShowAppointmentActivity.this));
//                        deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
//                        deleteItem.setId(DELETE_INDEX);
//                        menu.addMenuItem(deleteItem);
//                        break;
//                    case AppointmentAdapter.TYPE_OLD:
//                        SwipeMenuItem showItem = new SwipeMenuItem(
//                                ShowAppointmentActivity.this);
//                        showItem.setBackground(new ColorDrawable(Color.GREEN));
//                        showItem.setWidth((int) Utils.convertDpToPixel((float) 90, ShowAppointmentActivity.this));
//                        showItem.setTitleSize(18);
//                        showItem.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
//                        showItem.setId(SHOW_INDEX);
//                        menu.addMenuItem(showItem);
//                        break;
//                }
//            }
//        };
//
//// set creator
//        swipeMenuListView.setMenuCreator(creator);
//        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                Appointment appt = listAppointment.get(position);
//
//                switch (menu.getMenuItem(index).getId()) {
//                    case EDIT_INDEX:
//                        Intent intent = new Intent(ShowAppointmentActivity.this,
//                                EditAppointmentActivity.class);
//                        ArrayList<Integer> listLabtestId = new ArrayList<>();
//                        for (AppointmentDetail d : appt.getListApptDetail()) {
//                            if (d.getLabTestIds() != null) {
//                                listLabtestId.addAll(d.getLabTestIds());
//                            }
//                        }
//                        intent.putExtra(LIST_LABTEST_ID, listLabtestId);
//                        Bundle b = new Bundle();
//                        b.putSerializable(LIST_APPT_DETAIL, appt);
//                        intent.putExtras(b);
//
//                        startActivity(intent);
//                        break;
//                    case DELETE_INDEX:
//                        deleteAppt(appt.getAppointmentId());
//                        break;
//                    case SHOW_INDEX:
//                        Intent intentShow = new Intent(ShowAppointmentActivity.this,
//                                AppointmentResultActivity.class);
//                        intentShow.putExtra(APPT_ID, appt.getAppointmentId());
//                        startActivity(intentShow);
//                        break;
//                }
//                // false : close the menu; true : not close the menu
//                return false;
//            }
//        });
//
//        //endtest
//
//
//        swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
//        swipeMenuListView.setAdapter(adapter);
        listView.setAdapter(adapter);
    }

    @Override
    public String getMainTitle() {
        return "Lịch hẹn";
    }

    @Override
    public void bindView() {
        listView = findViewById(R.id.swipe_list_appointment);
    }

    @Override
    public void updateUIData(Object obj) {

    }

    public void deleteAppt(int appointmentId) {
        showLoading();
        APIServiceManager.getService(AppointmentService.class).
                deleteAppointment(appointmentId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<ResponseMessage>(this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    protected void onResponseSuccess(Response<ResponseMessage> response) {
                        if (response.body().isSuccess()) {
                            showMessage(response.body().getMessage());
                            callDataResource();
                        } else {
                            showMessage(response.body().getMessage());
                        }

                    }
                });
    }

    @Override
    protected void callDataResource() {
        showLoading();
        APIServiceManager.getService(AppointmentService.class)
                .getPatientAppointment(71)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySingleObserver<List<Appointment>>(this) {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               protected void onResponseSuccess(Response<List<Appointment>> listResponse) {
                                   if (listResponse.body() != null) {
                                       listAppointment.clear();
                                       listAppointment.addAll(listResponse.body());
                                       adapter.notifyDataSetChanged();
                                   }

                               }
                           }
                );
    }

    @Override
    public void onDeleteClick(View v, Appointment appt, int position) {
        deleteAppt(appt.getAppointmentId());
    }

    @Override
    public void onViewClick(View v, Appointment appt, int position) {
        Intent intentShow = new Intent(ShowAppointmentActivity.this,
                AppointmentResultActivity.class);
        intentShow.putExtra(APPT_ID, appt.getAppointmentId());
        startActivity(intentShow);
    }

    @Override
    public void onEditClick(View v, Appointment appt, int position) {
        Intent intent = new Intent(ShowAppointmentActivity.this,
                EditAppointmentActivity.class);
        ArrayList<Integer> listLabtestId = new ArrayList<>();
        for (AppointmentDetail d : appt.getListApptDetail()) {
            if (d.getLabTestIds() != null) {
                listLabtestId.addAll(d.getLabTestIds());
            }
        }
        intent.putExtra(LIST_LABTEST_ID, listLabtestId);
        Bundle b = new Bundle();
        b.putSerializable(LIST_APPT_DETAIL, appt);
        intent.putExtras(b);

        startActivity(intent);
    }
}

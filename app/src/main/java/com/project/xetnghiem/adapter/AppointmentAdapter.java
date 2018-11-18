package com.project.xetnghiem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.Appointment;
import com.project.xetnghiem.models.AppointmentDetail;
import com.project.xetnghiem.utilities.AppConst;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends BaseAdapter {
    public static final int TYPE_NEW = 0;
    public static final int TYPE_OLD = 1;

    private List<Appointment> data = new ArrayList<>();
    private LayoutInflater inflater;

    public AppointmentAdapter(Context context, List<Appointment> list) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = list;
    }

//    public void addItem(final AppointmentDetail appointment) {
//        data.add(appointment);
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getAppointmentId();
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return ((Appointment) getItem(position))
                .getStatus().equals(AppConst.APPOINTMENT_STATUS_NEW) ? TYPE_NEW : TYPE_OLD;
    }

    String tmp = "";

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AppointmentAdapter.ViewHolder holder = null;
        Appointment appointment = (Appointment) getItem(i);
        if (view == null) {
            holder = new AppointmentAdapter.ViewHolder();
            view = inflater.inflate(R.layout.item_appointment, null);
            holder.txtAppointmentCode = view.findViewById(R.id.txt_appointment_code);
            holder.txtPurpose = view.findViewById(R.id.txt_purpose);
            holder.txtDoctorName = view.findViewById(R.id.txt_doctor);
            holder.txtConclusion = view.findViewById(R.id.txt_conclusion);
//            switch (rowType) {
//                case TYPE_NEW:
//                    view = inflater.inflate(R.layout.item_appointment_detail, null);
//                    holder.textSampleName = view.findViewById(R.id.txt_sample_type);
//                    holder.textStartTime = view.findViewById(R.id.txt_start_time);
//                    view.setEnabled(false);
////                    holder.btnDelete = view.findViewById(R.id.btn_delete_appt);
////                    holder.btnUpdate = view.findViewById(R.id.btn_edit_appt);
//                    break;
//                case TYPE_OLD:
//
//                    view = inflater.inflate(R.layout.sniplet_item2, null);
////                    view.setEnabled(false);
//                    holder.textView = view.findViewById(R.id.textSeparator);
//                    holder.textSampleName = view.findViewById(R.id.txt_sample_type);
//                    holder.textStartTime = view.findViewById(R.id.txt_start_time);
//                    break;
//            }
            view.setTag(holder);
        } else {
            holder = (AppointmentAdapter.ViewHolder) view.getTag();
        }
        if (holder.txtAppointmentCode != null) {
            holder.txtAppointmentCode.setText(appointment.getAppointmentCode());
        }
        if (holder.txtPurpose != null) {
            holder.txtPurpose.setText(appointment.getTestPurpose());
        }
        if (holder.txtDoctorName != null) {
            holder.txtDoctorName.setText(appointment.getDoctorName());
        }
        if (holder.txtConclusion != null) {
            holder.txtConclusion.setText(appointment.getConclusion());
        }
        return view;
    }

    private static class ViewHolder {
        public TextView txtAppointmentCode;
        public TextView txtPurpose;
        public TextView txtDoctorName;
        public TextView txtConclusion;
    }
}

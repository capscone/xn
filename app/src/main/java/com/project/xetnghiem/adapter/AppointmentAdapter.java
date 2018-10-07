package com.project.xetnghiem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {
    private List<Appointment> appointments;
    private  Context context;
    private  LayoutInflater inflater;
    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.appointments = appointments;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_appointment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        if (appointment != null) {
            String startTime = appointment.getStartTime();
            String sampleType = appointment.getSampleType();
            holder.txtSampleType.setText(sampleType);
            holder.txtStartTime.setText(startTime);

        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtSampleType, txtStartTime, txtFinishDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtSampleType = itemView.findViewById(R.id.txt_sample_type);
            txtStartTime = itemView.findViewById(R.id.txt_start_time);
//            txtFinishDate = itemView.findViewById(R.id.txt_fin)
        }
    }
}

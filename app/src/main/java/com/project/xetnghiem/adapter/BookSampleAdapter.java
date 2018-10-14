package com.project.xetnghiem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.xetnghiem.R;
import com.project.xetnghiem.models.SampleDto;

import java.util.List;

public class BookSampleAdapter extends BaseAdapter {
    private List<SampleDto> sampleDtoList;
    private LayoutInflater inflater;

    public BookSampleAdapter(Context context, List<SampleDto> list) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sampleDtoList = list;
    }

    public int getCount() {
        return sampleDtoList.size();
    }

    @Override
    public Object getItem(int i) {
        return sampleDtoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return sampleDtoList.get(i).getSampleId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        SampleDto dto = (SampleDto) getItem(i);
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_book_sampletest, null);
            holder.txtDate = view.findViewById(R.id.txt_date_sample
            );
            holder.txtSampleName = view.findViewById(R.id.txt_sample_name);
            holder.spnTime = view.findViewById(R.id.spn_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (holder.txtSampleName != null) {
        holder.txtSampleName.setText(dto.getSampleName());

        }
        if (holder.txtDate != null) {
            holder.txtDate.setText(dto.getCloseTime()+"");
        }

        return view;
    }

    private static class ViewHolder {
        public TextView txtDate;
        public TextView txtSampleName;
        public Spinner spnTime;
    }
}

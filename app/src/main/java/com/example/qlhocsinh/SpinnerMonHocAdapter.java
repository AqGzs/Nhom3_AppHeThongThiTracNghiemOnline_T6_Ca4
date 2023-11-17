package com.example.qlhocsinh;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerMonHocAdapter extends ArrayAdapter<MonHoc> {
    int layoutID;
    Activity context;
    ArrayList<MonHoc> monHocs;

    public SpinnerMonHocAdapter(@NonNull Activity context, int resource, ArrayList<MonHoc> monHocs) {
        super(context, resource, monHocs);

        this.layoutID = resource;
        this.context = context;
        this.monHocs = monHocs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);

        ImageView imgMonHoc = convertView.findViewById(R.id.imgMonHoc);
        TextView tvTenMonHoc = convertView.findViewById(R.id.tvTenMonHoc);

        MonHoc monHoc = monHocs.get(position);
        imgMonHoc.setBackgroundResource(monHoc.getHinhAnh());
        tvTenMonHoc.setText(monHoc.getTenMH());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);

        ImageView imgMonHoc = convertView.findViewById(R.id.imgMonHoc);
        TextView tvTenMonHoc = convertView.findViewById(R.id.tvTenMonHoc);

        MonHoc monHoc = monHocs.get(position);
        imgMonHoc.setBackgroundResource(monHoc.getHinhAnh());
        tvTenMonHoc.setText(monHoc.getTenMH());

        return convertView;
    }
}

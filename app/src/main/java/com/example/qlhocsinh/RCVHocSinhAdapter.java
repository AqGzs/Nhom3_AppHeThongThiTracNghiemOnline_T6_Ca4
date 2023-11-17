package com.example.qlhocsinh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class RCVHocSinhAdapter extends RecyclerView.Adapter<RCVHocSinhAdapter.RCVHocSinhAdapterViewHolder>{
    private Context context;
    private ArrayList<HocSinh> hocSinhs;
    int layoutID;
    MyDatabase database;

    public RCVHocSinhAdapter(Context context, int layoutID, ArrayList<HocSinh> hocSinhs) {
        this.context = context;
        this.layoutID = layoutID;
        this.hocSinhs = hocSinhs;
        database = new MyDatabase(context);
    }

    @NonNull
    @Override
    public RCVHocSinhAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(layoutID, viewGroup, false);
        return new RCVHocSinhAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCVHocSinhAdapterViewHolder holder, int i) {
        HocSinh hocSinh = hocSinhs.get(i);
        if(hocSinh == null)
            return;

        if(hocSinh.get_gioiTinh() == 0){
            holder.imgHSItem.setBackgroundResource(R.drawable.ic_hsnam);
        } else {
            holder.imgHSItem.setBackgroundResource(R.drawable.ic_hsnu);
        }

        holder.tvMaHSItem.setText(Integer.toString(hocSinh.get_maHS()));
        holder.tvNameHSItem.setText(hocSinh.get_tenHS());

        if(layoutID == R.layout.layout_diemdanh_list){

            if(hocSinh.get_trangThai() == 0){
                holder.tvTrangThai.setText("Có mặt");
                holder.tvTrangThai.setTextColor(context.getResources().getColor(R.color.green));
            } else{
                holder.tvTrangThai.setText("Vắng");
                holder.tvTrangThai.setTextColor(context.getResources().getColor(R.color.red));
            }

            holder.btnEditTrangThai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.tvTrangThai.getText().equals("Có mặt")){
                        holder.tvTrangThai.setText("Vắng");
                        hocSinh.set_trangThai(1);
                        holder.tvTrangThai.setTextColor(context.getResources().getColor(R.color.red));
                    } else {
                        hocSinh.set_trangThai(0);
                        holder.tvTrangThai.setText("Có mặt");
                        holder.tvTrangThai.setTextColor(context.getResources().getColor(R.color.green));
                    }
                }
            });
        } else {
            holder.btnTrangThai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.btnTrangThai.getText().equals("Có mặt")){
                        hocSinh.set_trangThai(1);
                        holder.btnTrangThai.setText("Vắng");
                        holder.btnTrangThai.setTextColor(context.getResources().getColor(R.color.red));
                    } else {
                        hocSinh.set_trangThai(0);
                        holder.btnTrangThai.setText("Có mặt");
                        holder.btnTrangThai.setTextColor(context.getResources().getColor(R.color.green));
                    }
                }
            });
        }
    }

    public void setData(ArrayList<HocSinh> hocSinhs){
        this.hocSinhs = hocSinhs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return hocSinhs.size();
    }

    public  class RCVHocSinhAdapterViewHolder extends RecyclerView.ViewHolder{
        private Button btnTrangThai;
        private ImageView imgHSItem, btnEditTrangThai;
        private TextView tvNameHSItem;
        private TextView tvMaHSItem;
        private TextView tvTrangThai;

        public RCVHocSinhAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            btnTrangThai = itemView.findViewById(R.id.btnTrangThai);
            imgHSItem = itemView.findViewById(R.id.imgHSItem);
            tvNameHSItem = itemView.findViewById(R.id.tvNameHSItem);
            tvMaHSItem = itemView.findViewById(R.id.tvMaHSItem);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnEditTrangThai = itemView.findViewById(R.id.btnEditTrangThai);
        }
    }
}

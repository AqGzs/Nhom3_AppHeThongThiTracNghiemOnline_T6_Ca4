package com.example.qlhocsinh;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MonHocAdapter extends RecyclerView.Adapter<MonHocAdapter.MonHocViewHolder>{

    private Context mcontext;
    private ArrayList<MonHoc> monHocs;
    int layoutID;
    int classID;

    MyDatabase database;

    public MonHocAdapter(Context mcontext, int layoutID, ArrayList<MonHoc> monHocs) {
        this.mcontext = mcontext;
        this.layoutID = layoutID;
        this.monHocs = monHocs;
        database = new MyDatabase(mcontext);
    }

    public void setData(ArrayList<MonHoc> monHocs){
        this.monHocs = monHocs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MonHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(layoutID, parent, false);
        return new MonHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonHocViewHolder holder, int position) {
        MonHoc monHoc = monHocs.get(position);
        if(monHoc == null){
            return;
        }
        holder.tvName.setText(monHoc.getTenMH());
        holder.imgMH.setBackgroundResource(monHoc.getHinhAnh());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkDeleteMonHoc = database.deleteMonHoc(monHoc);
                if(checkDeleteMonHoc == true) {
                    monHocs.remove(monHoc);
                    notifyDataSetChanged();
                    Toast.makeText(mcontext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mcontext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(monHocs != null){
            return monHocs.size();
        }
        return 0;
    }
    public  class MonHocViewHolder extends RecyclerView.ViewHolder{
        private ImageButton btnDelete;
        private ImageView imgMH;
        private TextView tvName;

        public MonHocViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMH = itemView.findViewById(R.id.imgMonHoc);
            tvName = itemView.findViewById(R.id.tvTenMH);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
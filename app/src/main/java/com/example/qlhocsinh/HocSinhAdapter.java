package com.example.qlhocsinh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Locale;

public class HocSinhAdapter extends ArrayAdapter<HocSinh> {
    Activity context = null;
    ArrayList<HocSinh> arrHS = null;
    HocSinh hocSinh;
    Button btnTrangThai;
    int layoutID;

    public HocSinhAdapter(Activity context, int layoutID, ArrayList<HocSinh> arrHS) {
        super(context, layoutID, arrHS);
        this.context = context;
        this.layoutID = layoutID;
        this.arrHS = arrHS;
    }

    public View getView(int position, View convertView, ViewGroup listHS) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);

        getDataItem(position, convertView);

        if(layoutID == R.layout.layout_hocsinh){
            setBtnItem(convertView);
        }

        return convertView;
    }

    private void getDataItem(int position, View convertView){
        TextView name = (TextView) convertView.findViewById(R.id.tvNameHSItem);
        TextView maHS = (TextView) convertView.findViewById(R.id.tvMaHSItem);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgHSItem);

        hocSinh = arrHS.get(position);
        maHS.setText("Mã học sinh: " + Integer.toString(hocSinh.get_maHS()));
        name.setText(hocSinh.get_tenHS());

        if(hocSinh.get_gioiTinh() == 0){
            img.setImageResource(R.drawable.ic_hsnam);
        }
        else{
            img.setImageResource(R.drawable.ic_hsnu);
        }
    }

    private void setBtnItem(View convertView){
        setBtnInfoItem(convertView);
        setBtnEditItem(convertView);
        setBtnDeleteItem(convertView);
    }

    private void setBtnInfoItem(View convertView){
        ImageView btnInfoHSItem = (ImageView) convertView.findViewById(R.id.btnInfoHSItem);
        btnInfoHSItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HocSinhActivity.showDialogBaseHocSinh(context, R.layout.layout_dialog_info_hocsinh);

                TextView tvMaHS = HocSinhActivity.dialog.findViewById(R.id.tvMaHS);
                TextView tvTenHS = HocSinhActivity.dialog.findViewById(R.id.tvTenHS);
                TextView tvNgaySinhHS = HocSinhActivity.dialog.findViewById(R.id.tvNgaySinhHS);
                TextView tvTonGiaoHS = HocSinhActivity.dialog.findViewById(R.id.tvTonGiaoHS);
                TextView tvDiaChi = HocSinhActivity.dialog.findViewById(R.id.tvDiaChi);

                tvMaHS.setText(Integer.toString(hocSinh.get_maHS()));
                tvTenHS.setText(hocSinh.get_tenHS());
                tvNgaySinhHS.setText(hocSinh.get_ngaySinh());
                tvTonGiaoHS.setText(hocSinh.get_tonGiao());
                tvDiaChi.setText(hocSinh.get_diaChi());
            }
        });
    }

    private void setBtnEditItem(View convertView){
        ImageView btnEditHSItem = (ImageView) convertView.findViewById(R.id.btnEditHSItem);
        btnEditHSItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HocSinhActivity.showDialogBaseHocSinh(context, R.layout.layout_dialog_base_hocsinh);

                TextView tvTitleDialog = HocSinhActivity.dialog.findViewById(R.id.tvTitleDialog);
                tvTitleDialog.setText("Sửa học sinh");

                setBtnSubmitEdit(hocSinh);
            }
        });
    }

    private void setBtnDeleteItem(View convertView){
        ImageView btnDeleteHSItem = (ImageView) convertView.findViewById(R.id.btnDeleteHSItem);
        btnDeleteHSItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkDelete = HocSinhActivity.database.xoaHocSinh(hocSinh);

                if(checkDelete == true){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    HocSinhActivity.getDataHocSinh();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setBtnSubmitEdit(HocSinh hocSinh){
        Button btnSubmitEditHS = HocSinhActivity.dialog.findViewById(R.id.btnSubmitDialogHS);
        EditText edtTenHS = (EditText) HocSinhActivity.dialog.findViewById(R.id.edtTenHS);
        EditText edtNgaySinhHS = (EditText) HocSinhActivity.dialog.findViewById(R.id.edtNgaySinhHS);
        EditText edtTonGiao = (EditText) HocSinhActivity.dialog.findViewById(R.id.edtTonGiao);
        EditText edtDiaChi = (EditText) HocSinhActivity.dialog.findViewById(R.id.edtDiaChi);

        edtTenHS.setText(hocSinh.get_tenHS());
        edtNgaySinhHS.setText(hocSinh.get_ngaySinh());
        edtTonGiao.setText(hocSinh.get_tonGiao());
        edtDiaChi.setText(hocSinh.get_diaChi());
        btnSubmitEditHS.setText("Sửa");

        btnSubmitEditHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenHS = edtTenHS.getText().toString();
                String ngaySinh = edtNgaySinhHS.getText().toString();
                String tonGiao = edtTonGiao.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                int gioiTinh = HocSinhActivity.getValueRadio();

                if(!tenHS.isEmpty() && !ngaySinh.isEmpty() && gioiTinh != -1) {
                    hocSinh.set_tenHS(tenHS);
                    hocSinh.set_ngaySinh(ngaySinh);
                    hocSinh.set_tonGiao(tonGiao);
                    hocSinh.set_diaChi(diaChi);
                    hocSinh.set_gioiTinh(gioiTinh);
                    Boolean checkInsert = HocSinhActivity.database.suaHocSinh(hocSinh);

                    if(checkInsert == true)
                    {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        HocSinhActivity.dialog.dismiss();
                        HocSinhActivity.getDataHocSinh();
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Tên, ngày sinh, giới tính là bắt buộc!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

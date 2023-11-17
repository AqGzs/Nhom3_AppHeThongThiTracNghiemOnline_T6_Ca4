package com.example.qlhocsinh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassArrayAdapter extends ArrayAdapter<Class> {
    Activity context = null;
    ArrayList<Class> classArrayList = null;
    int layoutId;
    ListView listView;
    MyDatabase database;

    public ClassArrayAdapter(Activity context, int layoutId, ArrayList<Class> classArrayList, ListView listView) {
        super(context, layoutId, classArrayList);

        this.context = context;
        this.classArrayList = classArrayList;
        this.layoutId = layoutId;
        this.listView = listView;
        database = new MyDatabase(context);
    }

    public View getView(int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        ImageButton image = (ImageButton) convertView.findViewById(R.id.imgClass);
        TextView tvClass = (TextView) convertView.findViewById(R.id.btnClass);
        Class myClass = classArrayList.get(pos);

        if(myClass.getLop() >= 1 && myClass.getLop() <= 9){
            image.setBackgroundResource(R.drawable.ic_class_preschool);
        }
        else {
            image.setBackgroundResource(R.drawable.ic_class_highschool);
        }
        tvClass.setText(myClass.get_tenLop());

        return convertView;
    }
}

package com.android.saleplatform.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.saleplatform.R;
import com.android.saleplatform.beans.GoodsInfo;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<String> {
    int resourceId;

    //需要重新构造方法，这了resourceid 为item布局，可以后面加上显示在listview的数据
    public MyArrayAdapter(@NonNull Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String peron = getItem(position);
        //布局重用
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(peron);
        return view;

    }
}

class ViewHolder {
    TextView tv;

    public ViewHolder(View view) {
        tv = view.findViewById(R.id.spinner_text);

    }
}

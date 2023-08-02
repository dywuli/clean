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

public class MyArrayAdapter1 extends ArrayAdapter<GoodsInfo> {
    int resourceId;

    //需要重新构造方法，这了resourceid 为item布局，可以后面加上显示在listview的数据
    public MyArrayAdapter1(@NonNull Context context, int resource, List<GoodsInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GoodsInfo peron = getItem(position);
        //布局重用
        View view;
        ViewHolder1 holder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            holder = new ViewHolder1(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder1) view.getTag();
        }
        holder.tv.setText(peron.getGoodsName() + " NO::" + peron.getGoodsId());
        return view;

    }

    class ViewHolder1 {
        TextView tv;

        public ViewHolder1(View view) {
            tv = view.findViewById(R.id.spinner_text);

        }
    }
}



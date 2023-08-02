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
import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.GoodsInfo;

import java.util.List;

public class AddressArrayAdapter extends ArrayAdapter<AddressInfo> {
    int resourceId;

    //需要重新构造方法，这了resourceid 为item布局，可以后面加上显示在listview的数据
    public AddressArrayAdapter(@NonNull Context context, int resource, List<AddressInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AddressInfo peron = getItem(position);
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
        holder.tv.setText(peron.getAddressName() + " NO::" + peron.getAddressId());
        return view;

    }

    class ViewHolder1 {
        TextView tv;

        public ViewHolder1(View view) {
            tv = view.findViewById(R.id.spinner_text);

        }
    }
}



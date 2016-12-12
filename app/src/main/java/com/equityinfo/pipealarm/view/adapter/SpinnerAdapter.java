package com.equityinfo.pipealarm.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.equityinfo.pipealarm.model.bean.CodeBean;

import java.util.List;

/**
 * Created by user on 2016/12/9.
 */
public class SpinnerAdapter extends ArrayAdapter {
    private LayoutInflater infalter;
    private int resource;
    private int textViewResourceId;

    private List<CodeBean> target;

    public SpinnerAdapter(Context context, int resource,
                          int textViewResourceId, List<CodeBean> objects) {
        super(context, resource, textViewResourceId, objects);

        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        target = objects;

        infalter = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = infalter.inflate(resource, null);
        TextView text = (TextView) convertView
                .findViewById(textViewResourceId);
        text.setText(target.get(position).code);
        text.setSingleLine();
//            text.setBackgroundColor(Color.GREEN);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        if (convertView == null)
            convertView = infalter.inflate(
                    android.R.layout.simple_list_item_1, null);
        TextView text = (TextView) convertView
                .findViewById(android.R.id.text1);
        text.setText(target.get(position).code);
//            text.setBackgroundColor(Color.RED);
        return convertView;
    }

    @Override
    public int getCount() {
        return target.size();
    }
}



package com.example.prashanjeet.messmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PRASHANJEET on 08-01-2019.
 */

public class AppoArrayList extends ArrayAdapter<Student> {

    private Activity context;
    private List<Student> appoList;

    public AppoArrayList(Activity context, List<Student> appoList){
        super(context, R.layout.appointment_list_layout, appoList);
        this.context=context;
        this.appoList=appoList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.appointment_list_layout, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTilte);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        Student appo = appoList.get(position);
        textViewTitle.setText(appo.getName());
        textViewDate.setText(appo.getHostel());

        return listViewItem;
    }
}

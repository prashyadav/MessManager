package com.example.prashanjeet.messmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PRASHANJEET on 10-01-2019.
 */

public class ComplaindList extends ArrayAdapter<ComplaindClass> {
    private Activity context;
    private List<ComplaindClass> appoList;

    public ComplaindList(Activity context, List<ComplaindClass> appoList){
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

        ComplaindClass appo = appoList.get(position);
        textViewTitle.setText("Compaind ::\n"+appo.getMessage());
        textViewDate.setText("\n"+ "Name::"+appo.getName()+"\n");

        return listViewItem;
    }
}

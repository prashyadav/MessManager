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

public class ExpArrayList extends ArrayAdapter<AdminExpense>  {

    private Activity context;
    private List<AdminExpense> appoList;

    public ExpArrayList(Activity context, List<AdminExpense> appoList){
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

        AdminExpense appo = appoList.get(position);
        textViewTitle.setText(appo.getDesc() +"and  cost is   " +  appo.getCost());
        textViewDate.setText(appo.getDate());

        return listViewItem;
    }
}

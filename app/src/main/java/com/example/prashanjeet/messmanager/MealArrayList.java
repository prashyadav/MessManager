package com.example.prashanjeet.messmanager;

/**
 * Created by prajjwal-ubuntu on 5/1/19.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by prajjwal-ubuntu on 26/9/18.
 */

public class MealArrayList extends ArrayAdapter<Meal> {

    private Activity context;
    private List<Meal> appoList;

    public MealArrayList(Activity context, List<Meal> appoList){
        super(context, R.layout.meal_list_layout, appoList);
        this.context=context;
        this.appoList=appoList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.meal_list_layout, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTilte);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDesc);

        Meal meal = appoList.get(position);
        textViewTitle.setText(meal.title + "\n" + meal.getDate());
        textViewDate.setText(meal.description);

        return listViewItem;
    }
}


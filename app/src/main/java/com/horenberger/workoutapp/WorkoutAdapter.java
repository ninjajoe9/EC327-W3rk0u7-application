package com.horenberger.workoutapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkoutAdapter extends ArrayAdapter<Exercise> {
        public WorkoutAdapter(Context context, ArrayList<Exercise> exercises){
            super(context, 0, exercises);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            String value = getItem(position).getName() + "\n" + "   * Sets: " +  getItem(position).getSets() + "\n" + "   * Reps: " + getItem(position).getReps() + "\n" + "   * Weight: " + getItem(position).getWeight();

            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.workout_item, parent, false);
            }
            TextView curitem = (TextView) convertView.findViewById(R.id.workoutitem);
            curitem.setText(value);
            //set color based on whether it's done
            if (getItem(position).getDone())
                curitem.setBackgroundColor(Color.parseColor("#D8ACA9"));
            else
                curitem.setBackgroundColor(0);
            return curitem;
        }
}

package com.example.kb.lab4_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkoutArrayAdapter extends ArrayAdapter<WorkoutPartBase>  {

    static final int VIEW_TYPE_PAUSE = 0;
    static final int VIEW_TYPE_WORKOUT = 1;
    static final int VIEW_TYPE_COUNT = 2;

    public WorkoutArrayAdapter(Context context, ArrayList<WorkoutPartBase> parts) {
        super(context,0,parts);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        WorkoutPartBase part = getItem(position);
        if(part instanceof PausePart) {
            return VIEW_TYPE_PAUSE;
        } else {
            return VIEW_TYPE_WORKOUT;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WorkoutPartBase part = getItem(position);

        if(convertView == null) {
            int layoutId = 0;
            if(getItemViewType(position) == VIEW_TYPE_PAUSE) {
                layoutId = R.layout.pause_list_item;
            } else {
                layoutId = R.layout.workout_list_item;
            }
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }
        TextView time_display = convertView.findViewById(R.id.time_text);
        time_display.setText(String.valueOf(part.getLength()));
        return convertView;
    }
}

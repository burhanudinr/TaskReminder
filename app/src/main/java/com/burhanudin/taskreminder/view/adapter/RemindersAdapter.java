package com.burhanudin.taskreminder.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.burhanudin.taskreminder.R;
import com.burhanudin.taskreminder.model.Reminder;

import java.util.ArrayList;

public class RemindersAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Reminder> reminders;

    public RemindersAdapter(Activity activity, ArrayList<Reminder> reminders) {
        this.activity = activity;
        this.reminders = reminders;
    }

    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public Reminder getItem(int position) {
        return reminders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(activity);
            view = layoutInflater.inflate(R.layout.list_reminder, null);
        }

        ImageView imgReminder = view.findViewById(R.id.imgReminder);
        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvNama = view.findViewById(R.id.tvNama);

        final Reminder reminder = getItem(position);

        //imgReminder;
        imgReminder.setImageResource(R.drawable.ic_note);
        tvId.setText(String.valueOf(reminder.getId()));
        tvNama.setText(reminder.getNama());

        return view;
    }
}

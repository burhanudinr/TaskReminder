package com.burhanudin.taskreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.burhanudin.taskreminder.helper.AlarmHelper;
import com.burhanudin.taskreminder.helper.DatabaseHelper;
import com.burhanudin.taskreminder.model.Reminder;
import com.burhanudin.taskreminder.view.ReminderActivity;
import com.burhanudin.taskreminder.view.adapter.RemindersAdapter;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final int RQS_1 = 1;
    private RemindersAdapter remindersAdapter;
    private ArrayList<Reminder> reminderList = new ArrayList<>();
    private DatabaseHelper db;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        setAlarm();
    }

    private void goToDetailWithIntent() {
        boolean isDirectToDetail = getIntent().getBooleanExtra("isDirectToDetail", false);
        if (isDirectToDetail) {
            int idReminder = getIntent().getExtras().getInt("idReminder", 0);

            Intent intents = new Intent(getApplicationContext(), ReminderActivity.class);
            intents.putExtra("idReminder", idReminder);
            intents.putExtra("isExist", true);
            startActivity(intents);
        }
        Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, 3);
        calSet.set(Calendar.MINUTE, 20);
        calSet.set(Calendar.SECOND, 20);
        calSet.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(getBaseContext(), AlarmHelper.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
    }

    private void initUi() {
        fab = findViewById(R.id.fab);
        db = new DatabaseHelper(getApplicationContext());
        ListView lvReminder = findViewById(R.id.lvReminders);
        reminderList.addAll(db.getAllReminders());
        remindersAdapter = new RemindersAdapter(this, reminderList);
        lvReminder.setAdapter(remindersAdapter);
        lvReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reminder reminder = (Reminder) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
                intent.putExtra("idReminder", reminder.getId());
                intent.putExtra("isExist", true);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
                intent.putExtra("isExist", false);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}

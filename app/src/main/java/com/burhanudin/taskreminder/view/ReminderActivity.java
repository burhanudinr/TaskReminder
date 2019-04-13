package com.burhanudin.taskreminder.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.burhanudin.taskreminder.MainActivity;
import com.burhanudin.taskreminder.R;
import com.burhanudin.taskreminder.helper.AlarmHelper;
import com.burhanudin.taskreminder.helper.DatabaseHelper;
import com.burhanudin.taskreminder.model.Reminder;

import java.util.Calendar;
import java.util.Date;

public class ReminderActivity extends AppCompatActivity {

    final static int RQS_1 = 1;
    DatabaseHelper db;
    private TextView tvId;
    private EditText etId, etNama, etDsc, etTimeStamp;
    private Button btnTambah, btnHapus;
    private int hourSet = 0;
    private int minuteSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_reminderctivity);

        initUi();

        boolean isExist = getIntent().getExtras().getBoolean("isExist");
        final int idReminder = getIntent().getExtras().getInt("idReminder");
        if (isExist) {
            db = new DatabaseHelper(this);
            Reminder reminder = db.getReminder(idReminder);
            db.close();
            initData(reminder);
        } else {
            emtyData();
        }

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHelper(v.getContext());

                Reminder reminder = new Reminder();
                reminder.setId(idReminder);

                db.deleteReminder(reminder);
                db.close();

                backToMainActivity();
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareReminder();
                db = new DatabaseHelper(v.getContext());
                boolean isSuccess = db.insertReminder(prepareReminder());
                if (isSuccess) {
                    Toast.makeText(ReminderActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                    setAlarm(prepareReminder());
                }
                backToMainActivity();
            }
        });

        etTimeStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etTimeStamp.setText((String.format("%02d:%02d", selectedHour, selectedMinute)));
                        hourSet = selectedHour;
                        minuteSet = selectedMinute;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void emtyData() {
        tvId.setVisibility(View.GONE);
        etId.setVisibility(View.GONE);

        btnTambah.setVisibility(View.VISIBLE);
        btnHapus.setVisibility(View.GONE);
    }

    private void initData(Reminder reminder) {
        etId.setText(String.valueOf(reminder.getId()));
        etNama.setText(reminder.getNama());
        etDsc.setText(reminder.getDsc());
        etTimeStamp.setText(reminder.getTimestamp());

        etId.setEnabled(false);
        etNama.setEnabled(false);
        etDsc.setEnabled(false);
        etTimeStamp.setEnabled(false);

        btnTambah.setVisibility(View.GONE);
        btnHapus.setVisibility(View.VISIBLE);
    }

    private void initUi() {
        tvId = findViewById(R.id.tvId);

        etId = findViewById(R.id.etId);
        etNama = findViewById(R.id.etNama);
        etDsc = findViewById(R.id.etDsc);
        etTimeStamp = findViewById(R.id.etTimeStamp);

        btnHapus = findViewById(R.id.btnHapus);
        btnTambah = findViewById(R.id.btnTambah);
    }

    private void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private Reminder prepareReminder() {
        Reminder reminder = new Reminder();
        reminder.setNama(etNama.getText().toString());
        reminder.setDsc(etDsc.getText().toString());
        reminder.setTimestamp(etTimeStamp.getText().toString());
//        reminder.setPathimg();
        return reminder;
    }

    private void setAlarm(Reminder reminder) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY, hourSet);
        cal_alarm.set(Calendar.MINUTE, minuteSet);
        cal_alarm.set(Calendar.SECOND, 0);
        if (cal_alarm.before(cal_now)) {
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(ReminderActivity.this, AlarmHelper.class);
        intent.putExtra("nama", reminder.getNama());
        intent.putExtra("idReminder", reminder.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0, intent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);

    }
}

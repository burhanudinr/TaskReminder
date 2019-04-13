package com.burhanudin.taskreminder.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.burhanudin.taskreminder.MainActivity;
import com.burhanudin.taskreminder.R;

public class AlarmHelper extends BroadcastReceiver {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    public static final int REQUEST_CODE = 0;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    @Override
    public void onReceive(Context context, Intent intent) {
        String nama = intent.getStringExtra("nama");
        int idReminder = intent.getIntExtra("idReminder", 0);
        if (nama != null) {
            Intent intentToDetail = new Intent(context, MainActivity.class);
            intentToDetail.putExtra("isExist", true);
            intentToDetail.putExtra("idReminder", idReminder);
            intentToDetail.putExtra("isDirectToDetail", true);
            intentToDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentToDetail,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.drawable.ic_add);
            mBuilder.setContentTitle("Reminder!")
                    .setContentText(nama)
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent);

            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300});
                assert mNotificationManager != null;
                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(REQUEST_CODE, mBuilder.build());
        }
    }
}

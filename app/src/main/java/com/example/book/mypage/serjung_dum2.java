package com.example.book.mypage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.example.book.R;

public class serjung_dum2 extends BroadcastReceiver {

    private static final String CHANNEL_ID = "notice_notification_channel";
    private static final String CHANNEL_NAME = "Notice Notifications";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("serjung_dum2", "Notice notification triggered"); // 디버깅 로그

        // 알림 채널 생성
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("공고 알림")
                .setContentText("공고 알림입니다")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        manager.notify(2, notification);
    }
}

package com.and.dalrat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    /*
     * Activity가 onResume 상태가 아닐 때는 Cloud Message에서 전송한 메시지는 수신되지 않는다.
     */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String from = remoteMessage.getFrom();
        String messageId = remoteMessage.getMessageId();
        String messageType = remoteMessage.getMessageType();

        Map<String, String> map = remoteMessage.getData();
        int size = map.size();
        Log.d(TAG, "RemoteMessage from : " + from);
        Log.d(TAG, "RemoteMessage id : " + messageId);
        Log.d(TAG, "RemoteMessage type : " + messageType);
        Log.d(TAG, "RemoteMessage size : " + size);
        if (size > 0) {
            JSONObject jsonObject = new JSONObject(map);
            Log.d(TAG, "RemoteMessage data : " + jsonObject.toString());
        }

        String title = map.get("title");
        String body = map.get("body");
        sendNotification(title, body);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            String title2 = notification.getTitle();
            String body2 = notification.getBody();
            Log.d(TAG, "RemoteMessage notification title : " + title2);
            Log.d(TAG, "RemoteMessage notification body : " + body2);
        }
    }

    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "1")
                        .setSmallIcon(R.mipmap.dalrat_round_120)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(sound)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "PUSH 알림", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "TOKEN : " + s);
    }


}
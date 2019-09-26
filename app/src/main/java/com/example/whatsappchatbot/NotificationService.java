package com.example.whatsappchatbot;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.whatsappchatbot.models.Action;
import com.example.whatsappchatbot.services.BaseNotificationListener;
import com.example.whatsappchatbot.utils.NotificationUtils;

import java.io.ByteArrayOutputStream;


public class NotificationService extends BaseNotificationListener {

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg", "Notification Removed");
    }

    @Override
    protected boolean shouldAppBeAnnounced(StatusBarNotification statusBarNotification) {
        String pack = statusBarNotification.getPackageName();
        String ticker = "";
        String text = "";
        if (statusBarNotification.getNotification().tickerText != null) {
            ticker = statusBarNotification.getNotification().tickerText.toString();
        }
        Bundle extras = statusBarNotification.getNotification().extras;
        String title = extras.getString("android.title");
        text = (String) extras.getCharSequence("android.text");
        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
        Bitmap id = statusBarNotification.getNotification().largeIcon;


        Log.i("Package", pack);
        Log.i("Ticker", ticker);
        Log.i("Title", title);
        Log.i("Text", text);

        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("ticker", ticker);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);
        if (id != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            id.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            msgrcv.putExtra("icon", byteArray);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
        try {
            Action action = NotificationUtils.getQuickReplyAction(statusBarNotification.getNotification(), "com.whatsapp");
            if (action != null) {
                action.sendReply(context, "Hello");
            } else Toast.makeText(context, "object is null", Toast.LENGTH_SHORT).show();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
        return false;
    }

    @Override
    protected void onNotificationPosted(StatusBarNotification sbn, String dismissKey) {

    }

}

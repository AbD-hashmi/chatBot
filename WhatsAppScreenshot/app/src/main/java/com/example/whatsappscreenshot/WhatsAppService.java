package com.example.whatsappscreenshot;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class WhatsAppService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        /*if (Helper.isAppRunningSecond(WhatsAppService.this)) {
                            Log.e("THROW", "App is running");
                        } else {
                            Log.e("THROW", "App is not running");
                            // App is not running
                        }*/
                        if (Helper.retriveNewApp(WhatsAppService.this).equalsIgnoreCase("com.whatsapp")) {

                        }
                        Log.e("appH", Helper.retriveNewApp(WhatsAppService.this));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        return START_STICKY;
    }

}

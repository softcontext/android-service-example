package com.example.a34_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

public class MyBoundService extends Service {
    private static String LOG_TAG = "MyBoundService";
    private Chronometer mChronmeter;
    IBinder iBinder = new MyBinder();

    //아래 Binder는 iBinder인터페이스 구현체이다.
    class MyBinder extends Binder {
        MyBoundService getService() {
            return MyBoundService.this; //서비스객체리턴
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "MyBoundService onBind...");
        // 바인드서비스를 요청하는 대상에게 서비스제공자가 바인더객체를 리턴한다.
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "MyBoundService onCreate...");
        mChronmeter = new Chronometer(this);
        mChronmeter.setBase(SystemClock.elapsedRealtime());
        mChronmeter.start();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(LOG_TAG, "MyBoundService onRebind...");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG, "MyBoundService onUnbind...");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "MyBoundService onDestroy...");
        mChronmeter.stop();
    }

    public String getTimStamp() {
        long timeMillis = SystemClock.elapsedRealtime() - mChronmeter.getBase();
        int hours = (int) timeMillis / 3600000;
        int minutes = (int) (timeMillis - hours * 3600000) / 60000;
        int seconds = (int) (timeMillis - hours * 3600000 - minutes * 60000) / 1000;
        int millis = (int) (timeMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);

        return hours + ":" + seconds + ":" + millis;
    }
}


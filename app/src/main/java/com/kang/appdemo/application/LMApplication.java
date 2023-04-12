package com.kang.appdemo.application;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kang.appdemo.util.LMSysUtil;
import com.kang.appdemo.util.UIUtils;

public class LMApplication extends Application {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.init(this);
        Fresco.initialize(this);
        Log.e("GlobalReceiver", "application-> ThreadName:" + Thread.currentThread());
        Log.e("GlobalReceiver", "application-> ProcessName:" + LMSysUtil.getProcessName(this));
        Log.e("GlobalReceiver", "application-> CPU_COUNT:" + CPU_COUNT);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(this, "Application.Over!!!", Toast.LENGTH_SHORT).show();
    }


}

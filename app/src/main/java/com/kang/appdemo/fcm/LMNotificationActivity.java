package com.kang.appdemo.fcm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LMNotificationActivity extends AppCompatActivity {
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("FCMDemo", "LMNotificationActivity.onNewIntent...");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("FCMDemo", "LMNotificationActivity...");
        TextView textView = new TextView(this);
        String str = "hello-FCM-Notification Thread:" + Thread.currentThread();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            str += (",Process:" + this.getApplication().getProcessName());
        }
        textView.setText(str);
        setContentView(textView);
    }
}

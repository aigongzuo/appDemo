package com.kang.appdemo.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class GlobalReceiver extends BroadcastReceiver {
    private final static String ACTION_NETWORK = "android.net.conn.CONNECTIVITY_CHANGE";
    private static final String TAG = "GlobalReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive:" + intent.getAction());
//        if (intent.getAction().equals(ACTION_NETWORK)) {
//            // 网络变化时, 刷新当前页面.
//            refreshWhenNetChanged(context);
//        }
    }

    private void refreshWhenNetChanged(Context context) {
        boolean isOnline = false;
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectManager.getActiveNetworkInfo();
        //里面如果有参数,如ConnectivityManager.TYPE_WIFI,就是判断是否有WiFi网络.TYPE_后有很多选项,可根据需求填写.
        if (activeInfo != null) {
            isOnline = activeInfo.isConnected(); //判断是否有网
        }
        if (isOnline) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
            //获取当前所在的Activity名称
            String contextActivity = runningActivity.substring(runningActivity.lastIndexOf(".") + 1);
            Log.i(TAG, "contextActivity:" + contextActivity);

            //打开页面火刷新页面
//            Intent intent = new Intent(context, xxx.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            context.startActivity(intent);
        }
    }
}
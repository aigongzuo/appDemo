package com.kang.appdemo.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kang.appdemo.R;
import com.kang.appdemo.SplashActivity;
import com.kang.appdemo.util.LMSysUtil;

/**
 * activity-task查看
 * shell dumpsys activity activities
 *
 * 后台通知获取状态
 * adb shell dumpsys package com.kang.appdemo | grep stopped
 */
public class FCMService extends FirebaseMessagingService {
    private String mToken;

    @Override
    public void onCreate() {
        super.onCreate();
        L.e("消息服务已开启,ProcessName:" + LMSysUtil.getProcessName(this.getApplication()));
    }

    //获取到谷歌到token
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        L.e("onNewToken->Google token Refreshed token: $token");
        sendRegistrationToServer(token);
    }

    //  回传给服务器操作
    private void sendRegistrationToServer(String token) {
        mToken = token;
        //这里我做了本地保存，你可以在你需要到地方获取
        //ShareUtils.putString(getApplicationContext(), "GoogleToken", mToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        L.e("APP处于前台消息标题" + remoteMessage.getNotification().getTitle());
        L.e("APP处于前台消息内容" + remoteMessage.getNotification().getBody());

        L.e("Data消息（为空）" + remoteMessage.getData());
        L.e("服务器" + remoteMessage.getFrom());

        L.e("ProcessName:" + LMSysUtil.getProcessName(this.getApplication()));
        L.e("Thread:" + Thread.currentThread());

        //这个应该可以看懂
        if (remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null) {
            sendNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        } else {
            sendNotification(getApplicationContext(), remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    private void sendNotification(Context iContext, String messageTitle, String messageBody) {


        //跳转到你想要跳转到页面
        Intent intent = new Intent(this, LMNotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_MUTABLE);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setTicker(messageTitle)//标题
                        .setSmallIcon(R.mipmap.ic_launcher)//你的推送栏图标
                        .setContentTitle("notification")
                        .setContentText(messageBody)//内容
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        //这里如果需要的话填写你自己项目的，可以在控制台找到，强转成int类型
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

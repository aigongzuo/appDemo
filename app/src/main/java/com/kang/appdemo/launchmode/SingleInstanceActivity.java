package com.kang.appdemo.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kang.appdemo.databinding.ActivitySingleTaskBinding;
import com.kang.appdemo.util.LogUtil;

/**
 * Activity启动模式singleTask的理解
 * https://blog.csdn.net/fxjzzyo/article/details/109293098
 * <p>
 * 查看Activity Tasks情况
 * adb shell dumpsys activity
 */
public class SingleInstanceActivity extends AppCompatActivity {
    public static final String TAG = SingleInstanceActivity.class.getSimpleName();
    ActivitySingleTaskBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.logMethName(TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onCreate(savedInstanceState);
        binding = ActivitySingleTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.text.setText("SingleInstanceActivity");

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.logMethName(TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.logMethName(TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.logMethName(TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
    }
    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.logMethName(TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.logMethName(TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.logMethName(TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.logMethName(TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}

package com.kang.appdemo.launchmode;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kang.appdemo.databinding.ActivitySingleTaskBinding;

/**
 * Activity启动模式singleTask的理解
 * https://blog.csdn.net/fxjzzyo/article/details/109293098
 *
 * 查看Activity Tasks情况
 * adb shell dumpsys activity
 *
 */
public class SingleTaskActivity extends AppCompatActivity {
    ActivitySingleTaskBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}

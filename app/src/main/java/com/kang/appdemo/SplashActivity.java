package com.kang.appdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.kang.appdemo.concurrent.LMTask;
import com.kang.appdemo.launchmode.SingleInstanceActivity;
import com.kang.appdemo.launchmode.SingleTaskActivity;
import com.kang.appdemo.parcelable.ParcelableDemo;
import com.kang.appdemo.util.LMThreadUtil;

import java.lang.annotation.Target;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {
    public static String from = BuildConfig.source;
    private static final String TAG = SplashActivity.class.getSimpleName();
    private boolean isReady = false;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, TAG + ".onNewIntent...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, TAG + ".onCreate");

        try {
            String saStr = "Action:" + getIntent().getAction() + ",Categories:" + Arrays.toString(getIntent().getCategories().toArray());
            saStr += "Scheme:" + getIntent().getScheme() + ",Data:" + getIntent().getDataString();
            String extrasStr = null;
            if (getIntent().getExtras() != null) {
                extrasStr = showBundleData(getIntent().getExtras());
            }
            Log.e(TAG, "Intent:" + getIntent().toString());
            Log.e(TAG, "Intent.Flags:" + Integer.toHexString(getIntent().getFlags()));
            Log.e(TAG, saStr);
            Log.e(TAG, "ExtrasStr:" + extrasStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        setContentView(R.layout.activity_splash);

        // Set up an OnPreDrawListener to the root view.
        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        isReady = isFinishing() || isDestroyed();
                        // Check if the initial data is ready.
                        if (isReady) {
                            // The content is ready; start drawing.
                            content.getViewTreeObserver().removeOnPreDrawListener(this);
//                            Log.e("kang", "onPreDraw...：isReady" + isReady);
                            return true;
                        } else {
//                            Log.e("kang", "onPreDraw...：isReady" + isReady);
                            return false;
                        }
                    }
                });

        LMThreadUtil.runUIThreadDelayed(new LMTask() {
            @Override
            public void runInTryCatch() {
                SplashActivity.this.finish();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                ParcelableDemo.LoadDemo(intent);
                startActivity(intent);
            }
        }, 10);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, TAG + ".onDestroy");
    }

    public String showBundleData(Bundle bundle) {
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + "=" + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }
}
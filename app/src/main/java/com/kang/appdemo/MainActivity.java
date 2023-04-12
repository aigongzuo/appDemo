package com.kang.appdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.kang.appdemo.databinding.ActivityMainBinding;
import com.kang.appdemo.parcelable.ParcelableDemo;
import com.kang.appdemo.view.AnimationManager;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("FCMDemo", "MainActivity.onNewIntent...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("FCMDemo", "MainActivity...");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                // 在代码中使用
                ViewGroup arcView = findViewById(R.id.arc_view);
                new AnimationManager().startAdmin(arcView);
            }
        });

        shaderBitmap();
        ParcelableDemo.getData(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    @Override
//    public void onBackPressed() {
//        super.moveTaskToBack(true);
//    }

    void shaderBitmap() {
        // 渐变色数组，包含两个颜色，这里使用红色和透明度为 0 的颜色
//        int[] colors = {Color.RED, Color.TRANSPARENT};
//
//        // 创建 GradientDrawable 对象
//        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
//
//        // 设置 ImageView 的背景为 GradientDrawable 对象
//        binding.imgView.setImageResource(R.drawable.testimg);
//        binding.imgView.setBackground(gradientDrawable);


        ImageView imageView = binding.imgView;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.testimg);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        int maskColor = Color.parseColor("#AA000000"); // 遮罩颜色为黑色，透明度为 170
        Paint maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setColor(maskColor);

        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

// 使用 LayerDrawable 将 BitmapShader 和遮罩结合起来
        Drawable[] layers = new Drawable[2];
        layers[0] = new BitmapDrawable(getResources(), bitmap);
        layers[1] = new ColorDrawable(maskColor);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.progress);

// 将 LayerDrawable 设置为 ImageView 的背景
        imageView.setBackground(layerDrawable);

// 将 BitmapShader 设置为 ImageView 的 ColorFilter，并设置 Xfermode
        ColorFilter colorFilter = new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.DST_IN);
        imageView.setColorFilter(colorFilter);
        imageView.setImageBitmap(bitmap);

    }
}
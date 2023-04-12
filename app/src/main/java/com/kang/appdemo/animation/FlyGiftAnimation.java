package com.kang.appdemo.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;
import com.facebook.fresco.animation.drawable.BaseAnimationListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.kang.appdemo.R;

public class FlyGiftAnimation {
    private ImageView flyIcon;
    private SimpleDraweeView webpIcon;
    private ObjectAnimator flyXY;

    private Rect mFrom;
    private Rect mTo;
    String outUrl = "https://esx.esxscloud.com/liveglb/cloudres/android/gift_manual_click_animation.webp";

    public FlyGiftAnimation(ViewGroup viewGroup, Rect from, Rect to) {
        if (viewGroup == null || viewGroup.getContext() == null) {
            return;
        }
        this.mFrom = from;
        this.mTo = to;
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_fly_gift, viewGroup, true);
        flyIcon = root.findViewById(R.id.fly_icon);
        webpIcon = root.findViewById(R.id.webp_icon);

        flyIcon.setX(from.left);
        flyIcon.setY(from.top);
        flyIcon.getLayoutParams().width = from.width();
        flyIcon.getLayoutParams().height = from.height();


        webpIcon.getLayoutParams().width = to.width();
        webpIcon.getLayoutParams().height = to.height();
        webpIcon.setX(to.left);
        webpIcon.setY(to.top);

        int toTop = to.centerX() - from.width() / 2;
        int toLeft = to.centerY() - from.height() / 2;

        // 创建一个 PropertyValuesHolder 对象来控制 View 的 alpha 属性
        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha", 0.0f, 1f);
        // 创建一个 PropertyValuesHolder 对象来控制 View 的 scaleX 属性
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        // 创建一个 PropertyValuesHolder 对象来控制 View 的 scaleY 属性
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);

        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", toTop);
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", toLeft);

        flyXY = ObjectAnimator.ofPropertyValuesHolder(flyIcon,
                translationX,
                translationY,
                alphaHolder,
                scaleXHolder,
                scaleYHolder).setDuration(2000);

        flyXY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                flyIcon.setVisibility(View.GONE);
                startWebp(outUrl);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
    }

    public void startAnimation() {
        Log.e("kang", "flyXY.isStarted:" + flyXY.isStarted() + "flyXY.isRunning:" + flyXY.isRunning());
        if (flyIcon.getVisibility() == View.VISIBLE) {
            return;
        }
        flyIcon.setVisibility(View.VISIBLE);
        flyXY.start();
    }


    void startWebp(final String url) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (isEqualUrl(url)) {
                            if (animatable instanceof AnimatedDrawable2) {
                                AnimatedDrawable2 animatedDrawable = (AnimatedDrawable2) animatable;
                                animatedDrawable.setAnimationListener(new BaseAnimationListener() {
                                    @Override
                                    public void onAnimationStop(AnimatedDrawable2 drawable) {
                                        animationEnd();
                                    }

                                    @Override
                                    public void onAnimationFrame(AnimatedDrawable2 drawable, int frameNumber) {
                                        //Log.e("kang", "frameNumber:" + frameNumber);
                                        if (animatedDrawable.getFrameCount() - 1 == frameNumber) {
                                            animationEnd();
                                        }
                                    }

                                    private void animationEnd() {
                                        if (isEqualUrl(url)) {
                                            webpIcon.setController(null);
                                        }
                                    }
                                });
                                animatedDrawable.start();
                            }
                        }
                    }
                }).build();
        webpIcon.setTag(url);
        webpIcon.setController(controller);
    }

    public boolean isEqualUrl(String url) {
        if (!(webpIcon.getTag() instanceof String)) {
            return false;
        }
        String tagUrl = (String) webpIcon.getTag();
        if (tagUrl.equals(url)) {
            return true;
        }
        return false;
    }


    public void startAnimationBSR() {
        // 计算中间点的坐标
        // 创建二阶贝塞尔曲线路径
        Path path = new Path();
        path.moveTo(mFrom.left + mFrom.width() / 2, mFrom.top + mFrom.height() / 2);

        float controlX = mTo.centerX();
        float controlY = mFrom.centerY();

        path.quadTo(controlX, controlY,
                mTo.centerX(), mTo.centerY());
        PathMeasure pathMeasure = new PathMeasure(path, false); // 创建一个 PathMeasure 对象，并将其与路径关联

        // 创建ValueAnimator并设置路径插值器
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(10000);
//        animator.setInterpolator(PathInterpolatorCompat.create(path));
        // 监听动画进度并更新View的位置和缩放比例
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = animation.getAnimatedFraction();
                // 计算缩放比例
                float scale = 1 - progress;
                float[] pos = new float[2]; // 创建一个数组，用于存储路径上某个位置的坐标
                pathMeasure.getPosTan(pathMeasure.getLength() * progress, pos, null); // 获取当前进度下的位置坐标

                // 计算坐标位置
                float x = pos[0] - mFrom.width() / 2;
                float y = pos[1] - mFrom.height() / 2;

//                // 设置View的缩放比例和坐标位置
//                arcView.setScaleX(scale);
//                arcView.setScaleY(scale);
//                arcView.setX(x);
//                arcView.setY(y);
            }
        });
        // 启动动画
        animator.start();
    }
}

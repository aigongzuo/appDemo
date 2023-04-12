package com.kang.appdemo.view;

import android.graphics.Rect;
import android.view.ViewGroup;

import com.kang.appdemo.animation.FlyGiftAnimation;
import com.kang.appdemo.util.UIUtils;

public class AnimationManager {
    public void startAdmin(ViewGroup viewGroup) {
        Rect from = new Rect();
        from.right = UIUtils.dip2px(50);
        from.bottom = UIUtils.dip2px(50);

        Rect to = new Rect();
        to.left = UIUtils.dip2px(100);
        to.top = UIUtils.dip2px(400);
        to.right = to.left + UIUtils.dip2px(100);
        to.bottom = to.top + UIUtils.dip2px(100);

        FlyGiftAnimation animation = new FlyGiftAnimation(viewGroup, from, to);
        animation.startAnimation();
    }
}
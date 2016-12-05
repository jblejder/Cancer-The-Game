package com.kutapps.keyten.home.adapters.binding;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.databinding.BindingAdapter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class HomeBindingAdapter
{
    @BindingAdapter({"android:background", "bind:animate"})
    public static void bindColorBackground(View view, @ColorRes int colorRes, int duration)
    {
        Drawable background = view.getBackground();
        int colorTo = ContextCompat.getColor(view.getContext(), colorRes);
        if (duration > 0)
        {
            if (!(background instanceof ColorDrawable))
            {
                background = new ColorDrawable(colorTo);
            }
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), (
                    (ColorDrawable) background).getColor(), colorTo);
            colorAnimation.setInterpolator(new AccelerateInterpolator());
            colorAnimation.setDuration(duration);
            colorAnimation.addUpdateListener(animator -> view.setBackgroundColor((int) animator
                    .getAnimatedValue()));
            colorAnimation.start();
        }
        else
        {
            view.setBackgroundColor(colorTo);
        }
    }
}

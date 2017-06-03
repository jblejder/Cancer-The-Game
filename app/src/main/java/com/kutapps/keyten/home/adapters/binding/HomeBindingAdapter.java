package com.kutapps.keyten.home.adapters.binding;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.databinding.BindingAdapter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class HomeBindingAdapter {
    @BindingAdapter({"android:background", "animate"})
    public static void bindColorBackground(View view, ColorModel colorModel, int duration) {
        Drawable background = view.getBackground();

        Integer color = colorModel.getColor();
        if (color == null) {
            color = ContextCompat.getColor(view.getContext(), colorModel.getColorRes());
        }

        if (duration > 0) {
            if (!(background instanceof ColorDrawable)) {
                background = new ColorDrawable(color);
            }

            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), (
                    (ColorDrawable) background).getColor(), color);
            colorAnimation.setInterpolator(new AccelerateInterpolator());
            colorAnimation.setDuration(duration);
            colorAnimation.addUpdateListener(animator -> view.setBackgroundColor((int) animator
                    .getAnimatedValue()));
            colorAnimation.start();
        } else {
            view.setBackgroundColor(color);
        }
    }
}

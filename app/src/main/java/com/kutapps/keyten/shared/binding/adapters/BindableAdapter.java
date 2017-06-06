package com.kutapps.keyten.shared.binding.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.BindingAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BindableAdapter {
    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"android:visibility", "duration"})
    public static void setVisibility(final ImageView view, Boolean isVisible, int duration) {
        if (isVisible) {
            view.setAlpha(0f);
            view.setVisibility(View.VISIBLE);
            view.animate().alpha(1).setDuration(duration).setListener(null);
        } else {
            view.animate().alpha(0).setDuration(duration).setListener(
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            animation.removeAllListeners();
                            super.onAnimationCancel(animation);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(View.GONE);
                            view.setImageDrawable(null);
                            super.onAnimationEnd(animation);
                        }
                    });
        }
    }

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }
}

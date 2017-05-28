package com.kutapps.keyten.shared.binding.adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.TypedValue;
import android.widget.TextView;

public class TextBindingAdapter {

    @BindingAdapter("android:textSize")
    public static void bindTextSize(TextView view, float textSize) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, pxFromDp(textSize, view.getContext()));
    }

    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }
}

package com.kutapps.keyten.shared.constants;

import android.support.annotation.ColorRes;

import com.kutapps.keyten.R;

public enum State
{
    Init(R.color.initLight, R.color.initLight),
    Noten(R.color.notenLight, R.color.notenDark),
    Keyten(R.color.keytenLight, R.color.keytenDark);

    private final
    @ColorRes
    int color;
    @ColorRes
    int colorDark;

    State(@ColorRes int color, int colorDark)
    {
        this.color = color;
        this.colorDark = colorDark;
    }

    @ColorRes
    public int getColorLight()
    {
        return color;
    }

    @ColorRes
    public int getColorDark()
    {
        return colorDark;
    }
}

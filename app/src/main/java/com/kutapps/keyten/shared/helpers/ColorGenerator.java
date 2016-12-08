package com.kutapps.keyten.shared.helpers;


import android.graphics.Color;

public class ColorGenerator
{
    public static int getColor(String text)
    {
        if (text == null)
        {
            return Color.WHITE;
        }
        float[] hsv = new float[3];
        float length = text.length();
        float hashCode = text.hashCode();
        hsv[0] = Math.abs(hashCode / length) % 361;
        hsv[1] = (Math.abs(hashCode * length) % 101) / 100;
        hsv[2] = (Math.abs(hashCode) % 101) / 100;
        return Color.HSVToColor(hsv);
    }
}

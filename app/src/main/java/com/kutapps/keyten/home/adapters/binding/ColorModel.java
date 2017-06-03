package com.kutapps.keyten.home.adapters.binding;

public class ColorModel {
    private final Integer color;
    private final int     colorRes;

    private ColorModel(int colorRes, Integer color) {
        this.colorRes = colorRes;
        this.color = color;
    }

    public static ColorModel res(int colorRes) {
        return new ColorModel(colorRes, null);
    }

    public static ColorModel color(int color) {
        return new ColorModel(0, color);
    }

    public int getColorRes() {
        return colorRes;
    }

    public Integer getColor() {
        return color;
    }
}

package com.kutapps.keyten.shared.binding.adapters;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class ImageBindingAdapter
{
    @BindingAdapter({"android:src"})
    public static void loadImage(ImageView view, String imageUrl)
    {
        Glide.with(view.getContext()).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter().into(view);
    }

    @BindingAdapter(value = {"android:src", "bind:circular"}, requireAll = false)
    public static void loadImage(ImageView view, String imageUri, boolean circular)
    {
        if (circular)
        {

            Glide.with(view.getContext()).load(imageUri).asBitmap().diskCacheStrategy
                    (DiskCacheStrategy.ALL).fitCenter().into(new BitmapImageViewTarget(view)
            {
                @Override
                protected void setResource(Bitmap resource)
                {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                            .create(view.getContext().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    view.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
        else
        {
            Glide.with(view.getContext()).load(imageUri).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter().into(view);
        }
    }

    @BindingAdapter({"android:src", "bind:error"})
    public static void loadImage(ImageView view, String imageUrl, Drawable errorRes)
    {
        Glide.with(view.getContext()).load(imageUrl).error(errorRes).diskCacheStrategy
                (DiskCacheStrategy.ALL).fitCenter().into(view);
    }

    @BindingAdapter("android:src")
    public static void bindBitmapImage(ImageView imageView, Bitmap imageResource)
    {
        if (imageResource == null)
        {
            imageView.setImageResource(android.R.color.transparent);
        }
        else
        {
            imageView.setImageBitmap(imageResource);
        }
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, @DrawableRes int resource)
    {
        imageView.setImageResource(resource);
    }
}

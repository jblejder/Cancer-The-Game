package com.kutapps.keyten.home.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kutapps.keyten.databinding.FragmentUserSettingsBinding;
import com.kutapps.keyten.home.dialogs.callbacks.IUserDialogCallback;

public class UserDialogFragment extends DialogFragment
{
    private FragmentUserSettingsBinding binding;
    IUserDialogCallback callback;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        callback = (IUserDialogCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentUserSettingsBinding.inflate(LayoutInflater.from(getContext()),
                container, false);
        binding.setHandler(callback);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return binding.getRoot();
    }
}

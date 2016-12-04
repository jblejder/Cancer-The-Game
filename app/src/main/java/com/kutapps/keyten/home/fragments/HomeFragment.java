package com.kutapps.keyten.home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kutapps.keyten.R;
import com.kutapps.keyten.databinding.FragmentHomeBinding;
import com.kutapps.keyten.home.dialogs.UserDialogFragment;
import com.kutapps.keyten.home.dialogs.callbacks.IUserDialogCallback;
import com.kutapps.keyten.home.fragments.handlers.IMainFragmentHandler;
import com.kutapps.keyten.home.viewmodels.HomeViewModel;
import com.kutapps.keyten.main.activities.callbacks.IMainActivityCallback;
import com.kutapps.keyten.shared.fargments.BaseFragment;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements
        IUserDialogCallback, IMainFragmentHandler
{
    private static final String DIALOG_TAG = "dialogtag";

    private IMainActivityCallback callback;
    private HomeViewModel         model;

    public static HomeFragment newInstance()
    {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context)
    {
        callback = ((IMainActivityCallback) context);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        model = new HomeViewModel(callback.getModel());
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState)
    {
        binding.setModel(model);
        binding.setHandler(this);
    }

    @Override
    public void onClickUser()
    {
        new UserDialogFragment().show(getChildFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void onClickLogout()
    {
        callback.onClickLogout();
    }
}

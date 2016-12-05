package com.kutapps.keyten.login.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.kutapps.keyten.R;
import com.kutapps.keyten.databinding.FragmentLoginBinding;
import com.kutapps.keyten.main.activities.callbacks.IMainActivityCallback;
import com.kutapps.keyten.shared.fargments.BaseFragment;

public class LoginFragment extends BaseFragment<FragmentLoginBinding>
{
    private static final String TAG = LoginFragment.class.getSimpleName();

    IMainActivityCallback callback;

    public static LoginFragment newInstance()
    {
        return new LoginFragment();
    }

    @Override
    public void onAttach(Context context)
    {
        callback = ((IMainActivityCallback) context);
        super.onAttach(context);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState)
    {
        binding.signInButton.setOnClickListener(this::onClickLogin);
    }

    private void onClickLogin(View view)
    {
        callback.onLoginWithGoogle();
    }
}


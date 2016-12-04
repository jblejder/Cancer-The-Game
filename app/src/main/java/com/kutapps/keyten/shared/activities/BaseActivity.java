package com.kutapps.keyten.shared.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kutapps.keyten.shared.fargments.BaseFragment;


public abstract class BaseActivity extends AppCompatActivity
{
    protected <T extends ViewDataBinding> void loadFragment(BaseFragment<T> fragment, boolean
            addToBackStack)
    {
        if (fragment != null)
        {
            Fragment currentFragment = getCurrentFragment();

            if (currentFragment == null || !currentFragment.getClass().getName().equals(fragment
                    .getClass().getName()))
            {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(getFragmentLayoutResource(), fragment, fragment.getClass().getName());
                if (addToBackStack)
                {
                    ft.addToBackStack(fragment.getClass().getName());
                }
                ft.commit();
            }
        }
    }

    protected Fragment getCurrentFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentById(getFragmentLayoutResource());
    }

    protected abstract int getFragmentLayoutResource();

    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null)
        {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public void showKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        if (v != null)
        {
            imm.showSoftInput(v, 0);
        }
    }

    protected <T extends Activity> void startActivity(Class<T> activity)
    {
        startActivity(new Intent(this, activity));
    }
}

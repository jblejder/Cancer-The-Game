package com.kutapps.keyten.home.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.kutapps.keyten.R;
import com.kutapps.keyten.databinding.FragmentHomeBinding;
import com.kutapps.keyten.home.adapters.RecentAdapter;
import com.kutapps.keyten.home.dialogs.UserDialogFragment;
import com.kutapps.keyten.home.dialogs.callbacks.IUserDialogCallback;
import com.kutapps.keyten.home.viewmodels.HomeViewModel;
import com.kutapps.keyten.main.activities.callbacks.IMainActivityCallback;
import com.kutapps.keyten.shared.fargments.BaseFragment;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements IUserDialogCallback {
    private static final String DIALOG_TAG = "dialogtag";
    private static final long ANIM_LONG = 500;
    private static final long ANIM_MEDIUM = 330;
    private static final long ANIM_SHORT = 200;
    private static final long BUTTON_VISIBILITY = 5000;

    private IMainActivityCallback callback;
    public HomeViewModel model;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        callback = ((IMainActivityCallback) context);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new HomeViewModel(callback.getModel());
        model.state.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                switch (model.state.get()) {
                    case NotMine:
                    case Mine:
                        expandButton();
                        break;
                    case Init:
                    case Error:
                    default:
                        collapseButton();
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        binding.setModel(model);
        binding.btnUser.setOnClickListener(this::onClickUser);
        //binding.getRoot().setOnClickListener(v -> rootClicked());
        binding.btnGoToLeaderboards.setOnClickListener(v -> {
            hideButton();
        });

        RecentAdapter adapter = new RecentAdapter(model.recentOwners);
        binding.leaderboard.setAdapter(adapter);
        binding.leaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.leaderboard.setHasFixedSize(true);
    }


    public void onClickUser(View v) {
        new UserDialogFragment().show(getChildFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void onClickLogout() {
        callback.onClickLogout();
    }

    private void expandButton() {
        View b = binding.imgCancer;
        if (b.getVisibility() != View.VISIBLE) {
            b.setScaleX(0);
            b.setScaleY(0);
            b.setVisibility(View.VISIBLE);
            b.animate().scaleX(1).scaleY(1).setInterpolator(new OvershootInterpolator())
                    .setDuration(ANIM_MEDIUM);
        } else {
            b.animate().scaleX(1.1f).scaleY(1.1f).setInterpolator(
                    new AccelerateDecelerateInterpolator())
                    .setDuration(ANIM_SHORT).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    b.animate().scaleX(1).scaleY(1).setDuration(ANIM_SHORT).setInterpolator(
                            new AccelerateDecelerateInterpolator())
                            .setListener(null);
                }
            });
        }
    }

    private void collapseButton() {
        View b = binding.imgCancer;
        if (b.getVisibility() == View.VISIBLE) {
            b.animate().scaleX(0).scaleY(0).setInterpolator(new OvershootInterpolator())
                    .setDuration(ANIM_MEDIUM).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    b.setScaleX(1);
                    b.setScaleY(1);
                    b.setVisibility(View.VISIBLE);
                    animation.removeAllListeners();
                }
            });
        }
    }

    private void rootClicked() {
        if (binding.btnGoToLeaderboards.getVisibility() != View.VISIBLE) {
            showButton();
        }
    }

    private void hideButton() {
        Button b = binding.btnGoToLeaderboards;
        int translation = b.getHeight() + ((ViewGroup.MarginLayoutParams)
                b.getLayoutParams()).bottomMargin;
        b.animate().scaleX(0.8f).translationY(translation)
                .setInterpolator(
                        new DecelerateInterpolator()).setDuration(ANIM_LONG)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        b.setVisibility(View.INVISIBLE);
                        animation.removeAllListeners();
                    }
                });
    }

    private void showButton() {
        Button b = binding.btnGoToLeaderboards;
        int translation = b.getHeight() + ((ViewGroup.MarginLayoutParams)
                b.getLayoutParams()).bottomMargin;
        b.setTranslationY(translation);
        b.setVisibility(View.VISIBLE);
        b.setScaleX(0.8f);
        b.animate().scaleX(1).translationY(0).setDuration(ANIM_LONG).setInterpolator(
                new OvershootInterpolator())
                .setListener(null);
        binding.btnGoToLeaderboards.postDelayed(() -> {
            if (isAdded()) {
                hideButton();
            }
        }, BUTTON_VISIBILITY);
    }
}

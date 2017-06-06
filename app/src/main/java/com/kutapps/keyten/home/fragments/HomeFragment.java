package com.kutapps.keyten.home.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import com.kutapps.keyten.R;
import com.kutapps.keyten.databinding.FragmentHomeBinding;
import com.kutapps.keyten.home.adapters.RecentAdapter;
import com.kutapps.keyten.home.dialogs.UserDialogFragment;
import com.kutapps.keyten.home.dialogs.callbacks.IUserDialogCallback;
import com.kutapps.keyten.home.viewmodels.HomeFragmentViewModel;
import com.kutapps.keyten.main.activities.callbacks.IMainActivityCallback;
import com.kutapps.keyten.shared.fargments.BaseFragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements IUserDialogCallback {
    private static final String DIALOG_TAG                  = "dialogtag";
    private static final int    ANIM_LONG                   = 500;
    private static final int    ANIM_MEDIUM                 = 330;
    private static final int    ANIM_SHORT                  = 100;
    private static final int    LEADERBOARD_ANIMATION_DELAY = 5000;

    private IMainActivityCallback                 callback;
    @Inject
    public  HomeFragmentViewModel                 model;
    private BottomSheetBehavior<NestedScrollView> sheet;

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
        AndroidSupportInjection.inject(this);
        model.state.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                switch (model.state.get()) {
                    case NotMine:
                        expandButton();
                    case Mine:
                        if(binding.btnTHEBUTTON.getVisibility() != View.VISIBLE)
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
        binding.btnTHEBUTTON.setOnClickListener(this::onClickTheButton);

        RecentAdapter adapter = new RecentAdapter(model.recentOwners);
        binding.leaderboard.setAdapter(adapter);
        binding.leaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        sheet = BottomSheetBehavior.from(binding.bottomSheet);
        sheet.setHideable(false);
        sheet.setPeekHeight((int) getResources().getDimension(R.dimen.peek_height));
        binding.bottomSheet.getBackground().setAlpha(0);
        sheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            int baseOffset = (int) getResources().getDimension(R.dimen.peek_height);
            int btnOffset = (int) (baseOffset / 1.3);
            int logoOffset = baseOffset / 2;
            float logoDelay = 0.2f;

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float offset = Math.abs(slideOffset);
                binding.bottomSheet.getBackground().setAlpha(
                        Math.min(255, (int) (Math.abs(slideOffset * 3)
                                * 255)));
                binding.btnTHEBUTTON.setTranslationY(-btnOffset * offset);

                binding.btnUser.setTranslationY(-logoOffset * Math.max(0, offset - logoDelay));
                binding.txtCancerInfo.setTranslationY(-btnOffset / 2 * offset);
            }
        });
        startLeaderboardAnimation();
    }


    public void onClickUser(View v) {
        new UserDialogFragment().show(getChildFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void onClickLogout() {
        callback.onClickLogout();
    }

    @UiThread
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

    @UiThread
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

    private boolean buttonAnimating = false;

    private void onClickTheButton(View button) {
        model.toggleState();
        if (!buttonAnimating) {
            buttonAnimating = true;
            button.animate().scaleX(0.9f).scaleY(0.9f).setInterpolator(
                    new AccelerateDecelerateInterpolator()).setDuration(ANIM_SHORT).setListener(
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animation.removeAllListeners();
                            button.animate().scaleX(1).scaleY(1).setDuration(ANIM_SHORT)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            animation.removeAllListeners();
                                            buttonAnimating = false;
                                        }
                                    });
                        }
                    });
        }
    }

    private void startLeaderboardAnimation() {
        if (sheet.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1, 0).setDuration(ANIM_LONG +
                    ANIM_MEDIUM);
            valueAnimator.addUpdateListener(
                    animation -> {
                        float animatedValue = ((float) animation.getAnimatedValue());
                        binding.bottomSheet.getBackground().setAlpha((int) (animatedValue * 255));
                    });
            valueAnimator.start();
            binding.leaderboard.animate().translationY(-getResources().getDimension(R.dimen
                    .activity_bottom_margin)).setDuration(ANIM_MEDIUM).setInterpolator(new
                    AccelerateDecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.leaderboard.animate().translationY(0).setDuration(ANIM_LONG)
                            .setInterpolator(new BounceInterpolator()).setListener(
                            new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    animation.removeAllListeners();
                                    binding.leaderboard.postDelayed(() -> {
                                        if (isAdded()) {
                                            startLeaderboardAnimation();
                                        }
                                    }, LEADERBOARD_ANIMATION_DELAY);
                                }
                            });
                }
            });
        } else {
            binding.leaderboard.postDelayed(() -> {
                if (isAdded()) {
                    startLeaderboardAnimation();
                }
            }, LEADERBOARD_ANIMATION_DELAY);
        }
    }
}

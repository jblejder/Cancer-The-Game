package com.kutapps.keyten.main.di.modules;

import com.kutapps.keyten.home.fragments.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainBuildersModule {
    @ContributesAndroidInjector
    public abstract HomeFragment contributeFeatureActivityInjector();
}

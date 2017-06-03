package com.kutapps.keyten.main.di.modules;

import com.kutapps.keyten.home.di.modules.FuckAndroidInjectionsModule;
import com.kutapps.keyten.home.di.scopes.HomeFragmentScope;
import com.kutapps.keyten.home.fragments.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainBuildersModule {
    @HomeFragmentScope
    @ContributesAndroidInjector(modules = FuckAndroidInjectionsModule.class)
    public abstract HomeFragment wtf();
}

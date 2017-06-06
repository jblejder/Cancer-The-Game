package com.kutapps.keyten.di.modules;

import com.kutapps.keyten.main.activities.MainActivity;
import com.kutapps.keyten.main.di.modules.MainBuildersModule;
import com.kutapps.keyten.main.di.scopes.MainActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {
    @MainActivityScope
    @ContributesAndroidInjector(modules = {MainBuildersModule.class})
    public abstract MainActivity contributeFeatureActivityInjector();
}
package com.kutapps.keyten.main.di;

import com.kutapps.keyten.main.activities.MainActivity;
import com.kutapps.keyten.main.di.modules.MainBuildersModule;
import com.kutapps.keyten.main.di.scopes.MainActivityScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

@MainActivityScope
@Subcomponent(modules = MainBuildersModule.class)
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    public abstract class Builder extends DispatchingAndroidInjector.Builder<MainActivity> {
    }
}

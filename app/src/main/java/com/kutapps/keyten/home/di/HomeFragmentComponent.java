package com.kutapps.keyten.home.di;

import com.kutapps.keyten.home.di.modules.FuckAndroidInjectionsModule;
import com.kutapps.keyten.home.di.scopes.HomeFragmentScope;
import com.kutapps.keyten.home.fragments.HomeFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@HomeFragmentScope
@Subcomponent(modules = FuckAndroidInjectionsModule.class)
public interface HomeFragmentComponent extends AndroidInjector<HomeFragment> {
    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<HomeFragment> {
    }
}

package com.kutapps.keyten.di;


import com.kutapps.keyten.KeytenApp;
import com.kutapps.keyten.di.modules.AppModule;
import com.kutapps.keyten.di.modules.BuildersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(KeytenApp application);

        AppComponent build();
    }

    void inject(KeytenApp app);
}

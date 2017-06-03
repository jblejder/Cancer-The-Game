package com.kutapps.keyten.di.modules;

import android.content.Context;

import com.kutapps.keyten.KeytenApp;
import com.kutapps.keyten.shared.database.IStorageRx;
import com.kutapps.keyten.shared.database.MockedRxStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    public Context provideContext(KeytenApp application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public IStorageRx storage() {
        //return new StorageRx();
        return new MockedRxStorage();
    }
}

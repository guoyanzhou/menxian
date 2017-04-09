package com.gzhou.menxian.deps;


import com.gzhou.menxian.home.HomeActivity;
import com.gzhou.menxian.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}

package com.gzhou.menxian.deps;


import com.gzhou.menxian.activity.AbstractFragmentActivity;
import com.gzhou.menxian.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(AbstractFragmentActivity.RestaurantListActivity restaurantListActivity);
}

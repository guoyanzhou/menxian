package com.gzhou.menxian.home;

import com.gzhou.menxian.models.RestaurantListData;
import com.gzhou.menxian.networking.NetworkError;
import com.gzhou.menxian.networking.Service;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RestaurantListPresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;

    public RestaurantListPresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getCityList() {
        view.showWait();

        Subscription subscription = service.getCityList(new Service.GetCityListCallback() {
            @Override
            public void onSuccess(List<RestaurantListData> restaurantListResponse) {
                view.removeWait();
                view.getityListSuccess(restaurantListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}

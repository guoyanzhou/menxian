package com.gzhou.menxian.networking;


import com.gzhou.menxian.models.RestaurantListData;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getCityList(final GetCityListCallback callback) {

        return networkService.getCityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<RestaurantListData>>>() {
                    @Override
                    public Observable<? extends List<RestaurantListData>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<RestaurantListData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<RestaurantListData> restaurantListResponse) {
                        callback.onSuccess(restaurantListResponse);

                    }
                });
    }

    public interface GetCityListCallback{
        void onSuccess(List<RestaurantListData> restaurantListResponse);

        void onError(NetworkError networkError);
    }
}

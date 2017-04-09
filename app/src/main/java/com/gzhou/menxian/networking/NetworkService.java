package com.gzhou.menxian.networking;


import com.gzhou.menxian.models.RestaurantListData;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {

    @GET("v2/restaurant/?lat=37.422740&lng=-122.139956")
    Observable<List<RestaurantListData>> getCityList();
}

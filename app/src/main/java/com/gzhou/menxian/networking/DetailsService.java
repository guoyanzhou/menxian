package com.gzhou.menxian.networking;

import com.gzhou.menxian.models.RestaurantData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by aaron on 4/8/17.
 */

public interface DetailsService {

    @GET("v2/restaurant/{id}")
    Observable<RestaurantData> getRestaurant(@Path("id") Integer id);

}

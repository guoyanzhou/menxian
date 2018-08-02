package com.gzhou.menxian.networking;

import com.gzhou.menxian.models.RestaurantData;
import com.gzhou.menxian.models.RestaurantListData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface LoadMoreService {
    @GET("v2/restaurant/?lat=37.422740&lng=-122.139956&offset=0&limit=50")
    Observable<List<RestaurantListData>> getCityList();
}

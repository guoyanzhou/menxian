package com.gzhou.menxian.home;

import com.gzhou.menxian.models.RestaurantListData;

import java.util.List;

public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getityListSuccess(List<RestaurantListData> restaurantListResponse);

}

package com.gzhou.menxian.home;

import com.gzhou.menxian.models.CityListData;

import java.util.List;

/**
 * Created by ennur on 6/25/16.
 */
public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getityListSuccess(List<CityListData> cityListResponse);

}

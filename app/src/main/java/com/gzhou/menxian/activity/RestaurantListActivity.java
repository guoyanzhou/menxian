package com.gzhou.menxian.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gzhou.menxian.BaseApp;
import com.gzhou.menxian.BuildConfig;
import com.gzhou.menxian.R;
import com.gzhou.menxian.fragment.DetailsFragment;
import com.gzhou.menxian.fragment.FavoritesFragment;
import com.gzhou.menxian.home.EndlessRecyclerViewScrollListener;
import com.gzhou.menxian.home.HomeView;
import com.gzhou.menxian.home.RestaurantListAdapter;
import com.gzhou.menxian.home.RestaurantListPresenter;
import com.gzhou.menxian.models.RestaurantData;
import com.gzhou.menxian.models.RestaurantListData;
import com.gzhou.menxian.networking.DetailsService;
import com.gzhou.menxian.networking.LoadMoreService;
import com.gzhou.menxian.networking.Service;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RestaurantListActivity extends BaseApp implements HomeView {
    static String Tag = RestaurantListActivity.class.getCanonicalName();
    private RecyclerView restaurantlist;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Inject
    public Service service;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        renderView();
        init();

        RestaurantListPresenter presenter = new RestaurantListPresenter(service, this);
        presenter.getCityList();

        getSupportActionBar().setTitle("Discover");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_btn_switch_to_on_mtrl_00012);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void renderView() {
        setContentView(R.layout.activity_home);
        restaurantlist = (RecyclerView) findViewById(R.id.restaurantlist);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    public void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        restaurantlist.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        restaurantlist.addOnScrollListener(scrollListener);
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        // Delay before notifying the adapter since the scroll listeners
// can be called while RecyclerView data cannot be changed.
        Toast.makeText(getApplicationContext(), "load more, offet = " + offset, Toast.LENGTH_LONG).show();
        getLoadMoredata(offset);
    }

    private void getLoadMoredata(Integer offset) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASEURL)
                .build();
        LoadMoreService loadMoreService = retrofit.create(LoadMoreService.class);
        Observable<List<RestaurantListData>> listRestaurantDataObservable = loadMoreService.getCityList();
        listRestaurantDataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RestaurantListData>>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<RestaurantListData> listRestaurantData) {
                        progressBar.setVisibility(View.GONE);
                        bindListRestaurant(listRestaurantData);
                    }
                });
    }

    private void bindListRestaurant(List<RestaurantListData> listRestaurantData) {
        RestaurantListAdapter adapter = (RestaurantListAdapter) restaurantlist.getAdapter();
        adapter.getData().addAll(listRestaurantData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getityListSuccess(List<RestaurantListData> restaurantListResponse) {

        RestaurantListAdapter adapter = new RestaurantListAdapter(getApplicationContext(), restaurantListResponse,
                new RestaurantListAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(RestaurantListData Item) {
                        Toast.makeText(getApplicationContext(), Item.getName(),
                                Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getSupportFragmentManager();

                        Fragment fragment = DetailsFragment.newInstance(Item.getId());
                        fm.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit();

                    }
                });

        restaurantlist.setAdapter(adapter);

    }
}

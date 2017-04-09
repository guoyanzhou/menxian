package com.gzhou.menxian.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gzhou.menxian.BaseApp;
import com.gzhou.menxian.R;
import com.gzhou.menxian.fragment.DetailsFragment;
import com.gzhou.menxian.models.CityListData;
import com.gzhou.menxian.networking.Service;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseApp implements HomeView {

    private RecyclerView restaurantlist;
    @Inject
    public Service service;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        renderView();
        init();

        HomePresenter presenter = new HomePresenter(service, this);
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
        restaurantlist.setLayoutManager(new LinearLayoutManager(this));
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
    public void getityListSuccess(List<CityListData> cityListResponse) {

        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), cityListResponse,
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(CityListData Item) {
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

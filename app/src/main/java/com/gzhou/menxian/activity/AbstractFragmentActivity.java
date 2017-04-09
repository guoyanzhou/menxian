package com.gzhou.menxian.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gzhou.menxian.BaseApp;
import com.gzhou.menxian.R;
import com.gzhou.menxian.fragment.DetailsFragment;
import com.gzhou.menxian.home.HomeView;
import com.gzhou.menxian.home.RestaurantListAdapter;
import com.gzhou.menxian.home.RestaurantListPresenter;
import com.gzhou.menxian.models.RestaurantListData;
import com.gzhou.menxian.networking.Service;

import java.util.List;

import javax.inject.Inject;

public abstract class AbstractFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public static class RestaurantListActivity extends BaseApp implements HomeView {

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
}
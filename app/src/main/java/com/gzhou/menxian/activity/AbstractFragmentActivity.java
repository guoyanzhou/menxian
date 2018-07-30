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
}
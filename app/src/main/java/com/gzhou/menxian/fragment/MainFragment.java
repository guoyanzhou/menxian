package com.gzhou.menxian.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gzhou.menxian.R;
import com.gzhou.menxian.activity.AbstractFragmentActivity;
import com.gzhou.menxian.activity.FavoritesActivity;
import com.gzhou.menxian.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends Fragment {
    MainFragmentViewholder viewholder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        viewholder = new MainFragmentViewholder(view);

        viewholder.discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.launchActivity(getContext(), AbstractFragmentActivity.RestaurantListActivity.class, null);
            }
        });
        viewholder.favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.launchActivity(getContext(), FavoritesActivity.class, null);
            }
        });
        return view;
    }

    static class MainFragmentViewholder {

        @InjectView(R.id.fragment_main_discover)
        LinearLayout discover;

        @InjectView(R.id.fragment_main_favorites)
        LinearLayout favorites;

        MainFragmentViewholder(@NonNull View view) {
            ButterKnife.inject(this, view);
        }
    }
}

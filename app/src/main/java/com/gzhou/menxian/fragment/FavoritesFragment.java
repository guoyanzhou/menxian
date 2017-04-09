package com.gzhou.menxian.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gzhou.menxian.BuildConfig;
import com.gzhou.menxian.R;
import com.gzhou.menxian.models.RestaurantData;
import com.gzhou.menxian.networking.DetailsService;
import com.gzhou.menxian.networking.Service;
import com.gzhou.menxian.util.DataLab;
import com.gzhou.menxian.util.MyDiskCache;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavoritesFragment extends Fragment {
    FavoritesFragmentViewHolder viewHolder;
    private FavoritesAdapter mAdapter;

    @Inject
    public Service service;

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);
        viewHolder = new FavoritesFragmentViewHolder(view);
        viewHolder.recyclerViewlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;

    }

    private void getDetailsToList(final RestaurantHolder holder, Integer id) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASEURL)
                .build();
        DetailsService detailsService = retrofit.create(DetailsService.class);
        Observable<RestaurantData> restaurantDataObservable = detailsService.getRestaurant(id);
        restaurantDataObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestaurantData>() {
                    @Override
                    public void onCompleted() {
                        viewHolder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(RestaurantData restaurantData) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        holder.bindRestaurant(restaurantData);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        DataLab dataLab = DataLab.get(getContext());

        List<Integer> idList = dataLab.getDatas(getContext());

        if (mAdapter == null) {
            mAdapter = new FavoritesAdapter(idList);
            viewHolder.recyclerViewlist.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RestaurantHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView restaurantNameTextView;
        private TextView descptTextView;
        private TextView statusTV;
        private ImageView imageView;

        private RestaurantData mRestaurantData;

        public RestaurantHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            restaurantNameTextView = (TextView) itemView.findViewById(R.id.restaurantName);
            descptTextView = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            statusTV = (TextView) itemView.findViewById(R.id.status);
        }

        public void bindRestaurant(RestaurantData restaurantData) {
            mRestaurantData = restaurantData;
            restaurantNameTextView.setText(mRestaurantData.getName());
            descptTextView.setText(mRestaurantData.getDescription());
            statusTV.setText(mRestaurantData.getStatus());

            String images = mRestaurantData.getCover_img_url();

            Glide.with(getContext())
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment existingFragment = fm.findFragmentById(R.id.fragment_container);
            Fragment fragment = DetailsFragment.newInstance(mRestaurantData.getId());
            if (existingFragment != null) {
                fm.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            } else {
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }

    private class FavoritesAdapter extends RecyclerView.Adapter<RestaurantHolder> {

        private List<Integer> mIdList;

        public FavoritesAdapter(List<Integer> idList) {
            mIdList = idList;
        }

        @Override
        public RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_home, parent, false);
            return new RestaurantHolder(view);
        }

        @Override
        public void onBindViewHolder(RestaurantHolder holder, int position) {
            getDetailsToList(holder, mIdList.get(position));
        }

        @Override
        public int getItemCount() {
            if (mIdList != null) {
                return mIdList.size();
            }
            viewHolder.progressBar.setVisibility(View.GONE);
            return 0;
        }
    }

    static class FavoritesFragmentViewHolder {

        @InjectView(R.id.restaurantlist)
        RecyclerView recyclerViewlist;

        @InjectView(R.id.progress)
        ProgressBar progressBar;

        FavoritesFragmentViewHolder(@NonNull View view) {
            ButterKnife.inject(this, view);
        }
    }
}

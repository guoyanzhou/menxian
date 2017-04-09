package com.gzhou.menxian.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gzhou.menxian.BuildConfig;
import com.gzhou.menxian.R;
import com.gzhou.menxian.models.RestaurantData;
import com.gzhou.menxian.networking.DetailsService;
import com.gzhou.menxian.util.DataLab;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailsFragment extends Fragment {

    public static final String RESTAURANT_ID = "restaurant_id";
    private Integer restaurantId;
    DetailsFragmentViewHolder viewHolder;

    public static DetailsFragment newInstance(Integer restaurantId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(RESTAURANT_ID, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantId = getArguments().getInt(RESTAURANT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        viewHolder = new DetailsFragmentViewHolder(view);
        getDetails(restaurantId);
        return view;
    }

    private void getDetails(Integer id) {
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
                        bindViewHolder(restaurantData);
                    }
                });
    }

    private void bindViewHolder(final RestaurantData restaurantData) {
        viewHolder.detailsDescription.setText(restaurantData.getDescription());
        viewHolder.ratingTV.setText(String.valueOf(restaurantData.getAverageRating()));
        viewHolder.statusTV.setText(restaurantData.getStatus());

        String images = restaurantData.getCover_img_url();

        Glide.with(getContext())
                .load(images)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(viewHolder.detailsImage);

        viewHolder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "add to favorites",
                        Toast.LENGTH_LONG).show();
                DataLab dataLab = DataLab.get(getContext());
                dataLab.putData(getContext(), restaurantId);
            }
        });
    }

    static class DetailsFragmentViewHolder {

        @InjectView(R.id.detailsImage)
        ImageView detailsImage;

        @InjectView(R.id.detailsDescription)
        TextView detailsDescription;

        @InjectView(R.id.detailsRating)
        TextView ratingTV;

        @InjectView(R.id.detailsStatus)
        TextView statusTV;

        @InjectView(R.id.detailsAddButton)
        Button addBtn;

        @InjectView(R.id.progress)
        ProgressBar progressBar;

        DetailsFragmentViewHolder(@NonNull View view) {
            ButterKnife.inject(this, view);
        }
    }
}

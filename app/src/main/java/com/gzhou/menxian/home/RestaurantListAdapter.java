package com.gzhou.menxian.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gzhou.menxian.R;
import com.gzhou.menxian.models.RestaurantListData;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<RestaurantListData> data;
    private Context context;

    public RestaurantListAdapter(Context context, List<RestaurantListData> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }


    @Override
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RestaurantListAdapter.ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.tvName.setText(data.get(position).getName());
        holder.tvDesc.setText(data.get(position).getDescription());
        holder.tvStatus.setText(data.get(position).getStatus());

        String images = data.get(position).getCover_img_url();

        Glide.with(context)
                .load(images)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(RestaurantListData Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvStatus;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.restaurantName);
            tvDesc = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            tvStatus = (TextView) itemView.findViewById(R.id.status);

        }


        public void click(final RestaurantListData restaurantListData, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(restaurantListData);
                }
            });
        }
    }


}

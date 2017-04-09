package com.gzhou.menxian.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gzhou.menxian.util.Utils;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class RestaurantData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cover_img_url")
    @Expose
    private String cover_img_url;

    @SerializedName("average_rating")
    @Expose
    private Float average_rating;


    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescriptions(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }

    public Float getAverageRating() {
        return average_rating;
    }

    public void setAverageRating(Float average_rating) {
        this.average_rating = average_rating;
    }

    @Override
    public String toString() {
        return Utils.getGson().toJson(this);
    }

    @Override
    public RestaurantData clone() {
        return Utils.getGson().fromJson(toString(), RestaurantData.class);
    }
}
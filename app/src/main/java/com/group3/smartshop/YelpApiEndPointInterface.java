package com.group3.smartshop;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/11/13.
 * ?term=bestbuy&latitude=32.8672972&longitude=-117.209346
 */

public interface YelpApiEndPointInterface {
    @GET("businesses/search")
    Call<YelpParser> getBusinesses(@Header("Authorization") String yelp_token,
                                   @Query("term") String search_term,
                                   @Query("latitude") double lat,
                                   @Query("longitude") double lgn);

}

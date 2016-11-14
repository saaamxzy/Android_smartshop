package com.group3.smartshop;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by Administrator on 2016/11/13.
 */

public interface YelpApiEndPointInterface {
    @GET("businesses/search?term=bestbuy&latitude=32.8672972&longitude=-117.209346")
    Call<YelpParser> getTest(@Header("Authorization") String yelp_token);
}

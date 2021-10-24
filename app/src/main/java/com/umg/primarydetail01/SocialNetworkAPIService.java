package com.umg.primarydetail01;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SocialNetworkAPIService
{
    @GET("user/list")
    Call<UserFetchResults> getUser(@Query("email") String email);
}

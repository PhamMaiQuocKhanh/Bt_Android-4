package com.example.bt_android_4;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("android/api.aspx")
    Call<LastIDModel> getLastIdData(@Query("action") String action);
}

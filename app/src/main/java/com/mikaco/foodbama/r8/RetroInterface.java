package com.mikaco.foodbama.r8;


import com.mikaco.foodbama.model.LoginModel;
import com.mikaco.foodbama.model.MainViewModel;
import com.mikaco.foodbama.model.VerifyModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroInterface {
    @FormUrlEncoded
    @POST("app/mainview")
    Call<MainViewModel> getMainConfig(@Field("Token") String token, @Field("AppVer") String appVer);

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginModel> getLogin(@Field("username") String username, @Field("password") String password, @Field("deviceid") String deviceId);

    @FormUrlEncoded
    @POST("auth/verifyphone")
    Call<VerifyModel> getVerify(@Field("username") String username, @Field("code") String code, @Field("Os") int os, @Field("osVer") String osVer, @Field("deviceId") String deviceId, @Field("appVer") String appVer);
}

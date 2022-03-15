package com.mikaco.foodbama.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arash on 1/7/2018.
 */

public class VerifyModel {
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("token")
    public String token;
}

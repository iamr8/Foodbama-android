package com.mikaco.foodbama.model;

import com.google.gson.annotations.SerializedName;

public class BaseModel {
    @SerializedName("bookmarked")
    public Boolean bookmarked;
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public int status;
    @SerializedName("token")
    public String token;
    @SerializedName("Update")
    public String update;
    @SerializedName("url")
    public String url;
}


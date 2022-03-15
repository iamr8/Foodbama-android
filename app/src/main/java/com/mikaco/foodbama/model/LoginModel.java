package com.mikaco.foodbama.model;


import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
}

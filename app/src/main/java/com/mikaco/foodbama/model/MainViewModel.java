package com.mikaco.foodbama.model;

import com.google.gson.annotations.SerializedName;
import com.mikaco.foodbama.r8.Enums;

import java.util.ArrayList;

public class MainViewModel  {
    @SerializedName("status")
    public int status;
    @SerializedName("event")
    public String event;
    @SerializedName("forceUpdate")
    public int forceUpdate;
    @SerializedName("sliders")
    public ArrayList<MainViewSliderGroupModel> sliders = new ArrayList<MainViewSliderGroupModel>();
    @SerializedName("name")
    public String name;
    @SerializedName("phone")
    public String phone;
    @SerializedName("topSliders")
    public ArrayList<MainViewTopSliderModel> topSliders = new ArrayList<MainViewTopSliderModel>();
    @SerializedName("updateLink")
    public String updateLink;
}


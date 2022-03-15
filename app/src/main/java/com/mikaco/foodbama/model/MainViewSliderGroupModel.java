package com.mikaco.foodbama.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainViewSliderGroupModel implements Parcelable {

    public static final Creator<MainViewSliderGroupModel> CREATOR = new Creator<MainViewSliderGroupModel>() {
        public MainViewSliderGroupModel createFromParcel(Parcel in) {
            return new MainViewSliderGroupModel(in);
        }

        public MainViewSliderGroupModel[] newArray(int size) {
            return new MainViewSliderGroupModel[size];
        }
    };

    @SerializedName("subject")
    public String subject;
    @SerializedName("items")
    public ArrayList<MainViewSliderGroupItemModel> items = new ArrayList<>();

    protected MainViewSliderGroupModel(Parcel in) {
        this.subject = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.subject);
    }
}

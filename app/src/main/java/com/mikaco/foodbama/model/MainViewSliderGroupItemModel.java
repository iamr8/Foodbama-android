package com.mikaco.foodbama.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MainViewSliderGroupItemModel  implements Parcelable{
    public static final Creator<MainViewSliderGroupItemModel> CREATOR = new Creator<MainViewSliderGroupItemModel>() {
        public MainViewSliderGroupItemModel createFromParcel(Parcel in) {
            return new MainViewSliderGroupItemModel(in);
        }

        public MainViewSliderGroupItemModel[] newArray(int size) {
            return new MainViewSliderGroupItemModel[size];
        }
    };
    @SerializedName("title")
    public String title;
    @SerializedName("subtitle")
    public String subtitle;
    @SerializedName("img")
    public String img;
    @SerializedName("isOffsale")
    public int isOffsale;
    @SerializedName("status")
    public int status;
    @SerializedName("id")
    public String id;

    protected MainViewSliderGroupItemModel(Parcel in) {
        this.title = in.readString();
        this.subtitle = in.readString();
        this.img = in.readString();
        this.isOffsale = in.readInt();
        this.id = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.subtitle);
        parcel.writeString(this.img);
        parcel.writeInt(this.isOffsale);
        parcel.writeString(this.id);
    }
}

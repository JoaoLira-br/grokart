package com.example.grokart.utils;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class KartItemModel implements Parcelable {
    private final String itemName;
    private final String itemPrice;
    private String quantityToBuy;
    private final String maxQuantity;
    public KartItemModel(String itemName, String itemPrice, String quantityToBuy, String maxQuantity){
        this.itemName = itemName;
        this.itemPrice = "$"+" "+itemPrice;
        this.quantityToBuy = quantityToBuy;
        this.maxQuantity = maxQuantity;


    }

    protected KartItemModel(Parcel in) {
        itemName = in.readString();
        itemPrice = in.readString();
        quantityToBuy = in.readString();
        maxQuantity = in.readString();
    }

    public static final Creator<KartItemModel> CREATOR = new Creator<KartItemModel>() {
        @Override
        public KartItemModel createFromParcel(Parcel in) {
            return new KartItemModel(in);
        }

        @Override
        public KartItemModel[] newArray(int size) {
            return new KartItemModel[size];
        }
    };

    public String getMaxQuantity() {
      return maxQuantity;
    }
    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getQuantityToBuy() {
        return quantityToBuy;
    }

    public void setQuantityToBuy(String quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemPrice);
        dest.writeString(quantityToBuy);
        dest.writeString(maxQuantity);
    }
}

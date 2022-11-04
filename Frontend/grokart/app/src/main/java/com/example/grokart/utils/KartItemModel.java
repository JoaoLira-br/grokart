package com.example.grokart.utils;




public class KartItemModel {
    private String itemName;
    private String itemPrice;
    private String quantityToBuy;
    private String maxQuantity;
    public KartItemModel(String itemName, String itemPrice, String quantityToBuy, String maxQuantity){
        this.itemName = itemName;
        this.itemPrice = "$"+" "+itemPrice;
        this.quantityToBuy = quantityToBuy;
        this.maxQuantity = maxQuantity;


    }
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
}

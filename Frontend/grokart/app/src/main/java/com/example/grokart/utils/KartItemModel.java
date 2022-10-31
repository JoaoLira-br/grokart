package com.example.grokart.utils;




public class KartItemModel {
    private String itemName;
    private String itemPrice;
    private String quantityToBuy;
    public KartItemModel(String itemName, String itemPrice, String quantityToBuy){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantityToBuy = quantityToBuy;

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

}

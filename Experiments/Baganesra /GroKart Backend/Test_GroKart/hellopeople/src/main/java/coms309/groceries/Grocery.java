package coms309.groceries;

public class Grocery {
    private String item;

    private String price;

    private String storeName;

    public Grocery(){

    }

    public Grocery(String item, String price, String storeName){
        this.item = item;
        this.price = price;
        this.storeName = storeName;
    }

    public String getItem() {return this.item;}

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {return this.price;}

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStoreName() {return this.storeName;}

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return item + " "
                + price + " "
                + storeName;
    }

}

package groKartApp.grocery;

public class Item {
    private String itemName;
    private int itemID;
    private String price;
    private String storeName;

    public Item(){

    }

    public Item(String itemName, int itemID, String price, String storeName){
        this.itemName = itemName;
        this.itemID = itemID;
        this.price = price;
        this.storeName = storeName;
    }

    public String getItemName() {return this.itemName;}
    public void setItemName(String itemName) {this.itemName = itemName;}
    public int getItemID() {return this.itemID;}
    public void setItemID(int itemID) {this.itemID = itemID;}
    public String getPrice(){return this.price;}
    public void setPrice(String price) {this.price = price;}
    public String getStoreName() {return this.storeName;}
    public void setStoreName(String storeName) {this.storeName = storeName;}

    @Override
    public String toString(){
        return itemName + " "
                + itemID + " "
                + price + " "
                + storeName;
    }
}

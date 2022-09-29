package groKartApp.groKart_v2.lists;

import groKartApp.groKart_v2.users.AppUser;

public class ItemList {
    private String listName;
    private int listID;
    private String[][] items;
    private int id;
    private AppUser particularAppUser;

    public ItemList(){

    }

    public String getListName() {return this.listName;}
    public void setListName(String listName) {this.listName = listName;}
    public int getListID() {return this.listID;}
    public void setListID(int listID) {this.listID = listID;}
    public String[][] getItems() {return this.items;}
    public void setItems(String[][] items) {this.items = items;}
    public int getId() {return particularAppUser.getUserID();}
    public void setId(AppUser particularAppUser) {this.id = particularAppUser.getUserID();}

    @Override
    public String toString(){
        return listName + " "
                + listID + " "
                + items[0][0] + " " + "QTY:" + items[0][1]+ " "
                + id;
    }

}

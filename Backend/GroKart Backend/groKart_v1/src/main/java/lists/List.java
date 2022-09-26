package lists;

import users.User;

public class List {
    private String listName;
    private int listID;
    private String[][] items;
    private int id;
    private User particularUser;

    public List(){

    }

    public String getListName() {return this.listName;}
    public void setListName(String listName) {this.listName = listName;}
    public int getListID() {return this.listID;}
    public void setListID(int listID) {this.listID = listID;}
    public String[][] getItems() {return this.items;}
    public void setItems(String[][] items) {this.items = items;}
    public int getId() {return particularUser.getUserID();}
    public void setId(User particularUser) {this.id = particularUser.getUserID();}

    @Override
    public String toString(){
        return listName + " "
                + listID + " "
                + items[0][0] + " " + "QTY:" + items[0][1]+ " "
                + id;
    }

}

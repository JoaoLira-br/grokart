package groKartApp.groKart_v2.users;

import groKartApp.groKart_v2.lists.ItemList;

import javax.persistence.*;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userName;
    private int userID;
    private String emailAdd;
    private String password;
    private String name;
    private String privilege;
    private String store;
    private ItemList[] itemLists;

    public AppUser(){

    }

    public AppUser(String userName, int userID, String emailAdd, String password, String name, String privilege, String store, ItemList[] itemLists){
        this.userName = userName;
        this.userID = userID;
        this.emailAdd = emailAdd;
        this.password = password;
        this.name = name;
        this.privilege = privilege;
        this.store = store;
        this.itemLists = itemLists;
    }

    //------------------------------ Getter and Setter Functions --------------------------------- //

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public int getUserID(){
        return userID;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }
    public String getEmailAdd(){
        return emailAdd;
    }
    public void setEmailAdd(String emailAdd){
        this.emailAdd = emailAdd;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPrivilege(){
        return privilege;
    }
    public void setPrivilege(String privilege){
        this.privilege = privilege;
    }
    public String getStore(){
        return store;
    }
    public void setStore(String store){
        this.store = store;
    }
    public ItemList[] getLists(){
        return itemLists;
    }
    public void setLists(ItemList[] itemLists){
        this.itemLists = itemLists;
    }

    @Override
    public String toString(){
        return userName + " "
                + userID + " "
                + emailAdd + " "
                + password + " "
                + name + " "
                + privilege + " "
                + store + " "
                + itemLists.toString();
    }

}
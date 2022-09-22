package users;

import lists.List;
public class User {

    //TODO
    //Might need the @Entity, @ID, @GeneratedValue
    private String userName;
    private int userID;
    private String emailAdd;
    private String password;
    private String name;
    private String privilege;
    private String store;
    private List[] lists;

    public User(){

    }

    public User(String userName, int userID, String emailAdd, String password, String name, String privilege, String store, List[] lists){
        this.userName = userName;
        this.userID = userID;
        this.emailAdd = emailAdd;
        this.password = password;
        this.name = name;
        this.privilege = privilege;
        this.store = store;
        this.lists = lists;
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
    public List[] getLists(){
        return lists;
    }
    public void setLists(List[] lists){
        this.lists = lists;
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
                + lists.toString();
    }

}

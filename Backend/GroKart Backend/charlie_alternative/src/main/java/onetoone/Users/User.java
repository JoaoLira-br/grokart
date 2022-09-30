package onetoone.Users;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import onetoone.Stores.Store;

@Entity
public class User {

     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String emailAdd;
    private String password;
    private String displayName;
    private int privilege;
    /*
     * @OneToOne creates a relation between the current entity/table(Store with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to store within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called store
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store store;

    public User(String userName, String emailAdd, String password, String displayName, int privilege) {
        this.userName = userName;
        this.emailAdd = emailAdd;
        this.password = password;
        this.displayName = displayName;
        this.privilege = privilege;
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getEmailAdd(){
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd){
        this.emailAdd = emailAdd;
    }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

    public Store getStore(){
        return store;
    }

    public void setStore(Store store){
        this.store = store;
    }

    public void setPrivilege(int privilege) { this.privilege = privilege; }

    public int getPrivilege() { return this.privilege; }
    
}

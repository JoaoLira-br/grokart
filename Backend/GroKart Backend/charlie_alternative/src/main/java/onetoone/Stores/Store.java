package onetoone.Stores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Users.User;


@Entity
public class Store {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String storeName;
    private String address;

    /*
     * @OneToOne creates a relation between the current entity/table(Store) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/store objects (store->user->store->...)
     */
    @OneToOne
    @JsonIgnore
    private User user;

    public Store(String storeName, String address) {
        this.storeName = storeName;
        this.address = address;
    }

    public Store() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}

package groKart_app.Karts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groKart_app.Items.Item;
import groKart_app.Users.User;
import groKart_app.Users.UserRepository;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
*   many lists <----> many items
*
*   many lists <----> many users
*
*   many lists may be owned by one user
* */

@Entity
@Table(name = "Kart")
public class Kart {
//    @Autowired
//    UserRepository userRepository;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // TODO: one to one kart-store relation
//    private Store store;
    private String kartName;

    private boolean shared;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany(mappedBy = "karts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    @ManyToMany(mappedBy = "karts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    public Kart(User owner, String kartName) {
        this.owner = owner;
        this.kartName = kartName;
        users = new ArrayList<User>();
        items = new ArrayList<Item>();
    }
//    public Kart(String owner_username, String kartName) {
//        this.owner = userRepository.findByUserName(owner_username);
//        this.kartName = kartName;
//        users = new ArrayList<User>();
//        items = new ArrayList<Item>();
//    }
    public Kart(User owner, String kartName, ArrayList<Item> items, ArrayList<User> users) {
        this.owner = owner;
        this.kartName = kartName;
        this.items = items;
        this.users = users;
    }
//    public Kart(String owner_username, String kartName, ArrayList<Item> items, ArrayList<User> users) {
//        this.owner = userRepository.findByUserName(owner_username);
//        this.kartName = kartName;
//        this.items = items;
//        this.users = users;
//    }
    public Kart(String kartName) {
        this.kartName = kartName;
        users = new ArrayList<User>();
        items = new ArrayList<Item>();
    }

    public Kart(){}
    public int getId() {
        return id;
    }

    public String getName() {
        return kartName;
    }

    public void setName(String kartName) {
        this.kartName = kartName;
    }

    public List<Item> getItems() {
        return items;
    }
    public List<User> getUsers() { return users; }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public void setUsers(List<User> user) { this.users = users; }

    public void addItem(Item item) {
        this.items.add(item);
    }
    public void addUser(User user) { this.users.add(user); }

    // I think id will be better for this parameter
    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User getOwner() { return owner; }

    public void setOwner(User owner) {
        this.owner = owner;
        owner.getOwnedKarts().add(this);
    }

    public boolean isShared() { return shared; }

    public void setShared(boolean shared) { this.shared = shared; }

    @Override
    public String toString() {
        return id + "," + kartName + ",\n" + items;
    }
}

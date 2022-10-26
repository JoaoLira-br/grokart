package groKart_app.Karts;

import groKart_app.Items.Item;
import groKart_app.Users.User;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
*   many lists <----> many items
*
*   many lists <----> many users
* */

@Entity
@Table(name = "Kart")
public class Kart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // should be changed to a 1-1 or 1-many relation with a Store object
    private String kartName;

    @ManyToOne
    private User owner;
    @ManyToMany(mappedBy = "karts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    @ManyToMany(mappedBy = "karts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    public Kart(String kartName) {
        this.kartName = kartName;
        users = new ArrayList<User>();
        items = new ArrayList<Item>();
    }
    public Kart(String kartName, ArrayList<Item> items, ArrayList<User> users) {
        this.kartName = kartName;
        this.items = items;
        this.users = users;
    }
    public Kart() {
        users = new ArrayList<User>();
        items = new ArrayList<Item>();
    }

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
//        for (Item i : items) {
//            if (i.getName().equals(itemName)) {
//                items.remove(i);
//                return i;
//            }
//        }
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public String toString() {
        return id + "," + kartName + ",\n" + items;
    }
}

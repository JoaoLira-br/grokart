package groKart_app.Karts;

import groKart_app.Items.Item;


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

    private String kartName;

//    @OneToMany(mappedBy = "kart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<User> users;
    @OneToMany
    private List<Item> items;

    public Kart(String kartName) {
        this.kartName = kartName;
//        users = new ArrayList<User>();
        items = new ArrayList<Item>();
    }

    public Kart() {
//        users = new ArrayList<User>();
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

//    public List<User> getUsers() {
//        return users;
//    }

//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

//    public void addUser(User user) {
//        this.users.add(user);
//    }

//    // I think id will be better for this parameter
//    public User removeUser(String userName) {
//        for (User u : users) {
//            if (u.getUserName().equals(userName)) {
//                users.remove(u);
//                return u;
//            }
//        }
//        return null;
//    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    // I think id will be better for this parameter
    public Item removeItem(String itemName) {
        for (Item i : items) {
            if (i.getName().equals(itemName)) {
                items.remove(i);
                return i;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return id + "," + kartName + ",\n" + items;
    }
}

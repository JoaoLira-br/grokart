package groKart_app.Karts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groKart_app.Items.Item;
import groKart_app.Users.User;
import groKart_app.Users.UserRepository;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    private boolean publicity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany(mappedBy = "karts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    private HashMap<Item, Integer> quantities;

    public Kart(User owner, String kartName) {
        this.owner = owner;
        this.kartName = kartName;
        items = new ArrayList<Item>();
        quantities = new HashMap<Item, Integer>();
    }

    public Kart(User owner, String kartName, ArrayList<Item> items, ArrayList<User> users, HashMap<Item, Integer> quantities) {
        this.owner = owner;
        this.kartName = kartName;
        this.items = items;
        this.quantities = quantities;
    }

    public Kart(String kartName) {
        this.kartName = kartName;
        items = new ArrayList<Item>();
        quantities = new HashMap<Item, Integer>();
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

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public User getOwner() { return owner; }

    public void setOwner(User owner) {
        this.owner = owner;
        owner.getOwnedKarts().add(this);
    }

    public boolean getPublicity() { return publicity; }

    public void setPublicity(boolean publicity) { this.publicity = publicity; }

    public HashMap<Item, Integer> getQuantities() { return quantities; }

    public void setQuantity(Item item, Integer quantity) {
        quantities.put(item, quantity);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Item item : items) {
            total += quantities.get(item) * item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return id + "," + kartName + ",\n" + items;
    }
}

package groKart_app.Items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groKart_app.Stores.Store;
import groKart_app.Karts.Kart;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    @JoinColumn(name = "kart_id")
    @JsonIgnore
    private List<Kart> karts;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store store;

    private String name;
    private double price;
    private String storeName;
    private int quantity;

    public Item(String name, double price, String storeName, int quantity) {
        this.name = name;
        this.price = price;
        this.storeName = storeName;
        this.quantity = quantity;
        karts = new ArrayList<Kart>();
    }

    public Item() {
        karts = new ArrayList<Kart>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }



    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addKart(Kart kart) {
        karts.add(kart);
    }
    public void removeKart(Kart kart) {
        karts.remove(kart);
    }

    @Override
    public String toString() {
        return id + " "
                + name + " "
                + price + " "
                + storeName + " "
                + quantity;
    }
}

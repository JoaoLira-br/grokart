package groKart_app.Items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groKart_app.Stores.Store;

import javax.persistence.*;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;
    private String storeName;
    private int quantityAvailable;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;


    public Item(String name, double price, String storeName, int quantityAvailable) {
        this.name = name;
        this.price = price;
        this.storeName = storeName;
        this.quantityAvailable = quantityAvailable;
    }

    public Item() {

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

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}

package groKart_app.Stores;

import groKart_app.Items.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table (name = "Stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeId;

    private String storeName;

    @OneToMany (mappedBy = "storeName")
    private List<Item> items;


    public Store(int storeId, String storeName){
        this.storeId = storeId;
        this.storeName = storeName;
        items = new ArrayList<>();
    }
    public Store() {
        items = new ArrayList<>() ;
    }


    public int getStoreId() {return storeId;}

    public void setStoreId(int storeId) {this.storeId = storeId;}

    public String getStoreName() {return storeName;}

    public void setStoreName(String storeName) {this.storeName = storeName;}

    public List<Item> getItems() {return items;}

    public void setItems(List<Item> items) {this.items = items;}

    public void addItems(Item items){
        this.items.add(items);
    }



}



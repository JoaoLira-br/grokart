package groKart_app.Stores;

import groKart_app.Items.Item;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table (name = "Store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String storeName;

    /*
    @OneToMany(mappedBy = "store")
    private List<Item> items;
     */


    public Store(int id, String storeName){
        this.id = id;
        this.storeName = storeName;
        //items = new ArrayList<>();
    }
    public Store() {
        //items = new ArrayList<>() ;
    }


    public int getStoreId() {return id;}

    public void setStoreId(int storeId) {this.id = id;}

    public String getStoreName() {return storeName;}

    public void setStoreName(String storeName) {this.storeName = storeName;}

    //public List<Item> getItems() {return items;}

    //public void setItems(List<Item> items) {this.items = items;}

    //public void addItems(Item items){this.items.add(items);}

    //public void removeItems(Item items){this.items.remove(items);}

}



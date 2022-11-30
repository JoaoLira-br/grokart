package groKart_app.Stores;

import groKart_app.Items.Item;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
    @ApiModelProperty(notes="Store ID", name="storeId", value="2")
    private int id;

    @ApiModelProperty(notes="Store Name", name="storeName", required = true, value="Walmart")
    private String storeName;


    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(notes="List of Items", name="items")
    private List<Item> items;



    public Store(int id, String storeName){
        this.id = id;
        this.storeName = storeName;
        items = new ArrayList<>();
    }
    public Store() {
        items = new ArrayList<>() ;
    }


    public int getStoreId() {return id;}

    public void setStoreId(int id) {this.id = id;}

    public String getStoreName() {return storeName;}

    public void setStoreName(String storeName) {this.storeName = storeName;}

    public List<Item> getItems() {return items;}

    public void setItem(List<Item> items) {this.items = items;}

    public void addItems(Item items){this.items.add(items);}

    public void removeItems(Item items){this.items.remove(items);}


    @Override
    public String toString() {
        return id + " "
                + storeName + " "
                + items;
    }
}




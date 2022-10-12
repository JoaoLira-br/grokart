package groKart_app.Stores;

import groKart_app.Items.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoreController {
    @Autowired
    StoreRepository storeRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * GET ALL STORES IN THE DB
     *
     * @return
     */
    @GetMapping(path = "/stores")
    List<Store> getAllStores(){
        return storeRepository.findAll();
    }

    /**
     * POST A STORE
     * @param store
     * @return
     */
    @PostMapping(path = "/stores")
    String createStore(@RequestBody Store store){
        if(store == null || storeRepository.existsByStoreName(store.getStoreName()))
            return failure;
        storeRepository.save(store);
        return success;
    }

    /**
     * GET ITEMS IN THE STORE
     * @param storeName
     * @return
     *
    @GetMapping(path ="/stores/{storeName}/items")
    List<Item> returnStoreItems(@PathVariable String storeName){
        Store store = storeRepository.findByStoreName(storeName);
        return store.getItems();
    }

    /**
     * UPDATE STORE
     * @param storeName
     * @param storeRequest
     * @return
     */
    @PutMapping(path = "/stores/{storeName}")
    Store updateStore(@PathVariable String storeName, @RequestBody Store storeRequest){
        Store store = storeRepository.findByStoreName(storeName);
        if(store == null){return null;}
        storeRepository.deleteByStoreName(storeName);
        storeRepository.save(storeRequest);
        return storeRepository.findByStoreName(storeName);
    }

    /**
     * ASSIGN AN ITEM TO A STORE / ADD AN ITEM TO A STORE
     */
    @PutMapping(path = "/stores/{storeName}/items")
    String addItemToStore(@PathVariable String storeName, @RequestBody Item newItem){
        Store store = storeRepository.findByStoreName(storeName);
        if(store == null || newItem == null){return failure;}
        store.addItems(newItem);
        storeRepository.save(store);
        return success;
    }


    /**
     * REMOVE STORE
     * @param storeName
     * @return
     */
    @DeleteMapping(path = "/stores/{storeName}")
    String deleteStore(@PathVariable String storeName){
        storeRepository.deleteByStoreName(storeName);
        return success;
    }
}





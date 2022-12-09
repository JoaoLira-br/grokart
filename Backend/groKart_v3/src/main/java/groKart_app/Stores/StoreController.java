package groKart_app.Stores;

import groKart_app.Items.Item;
import groKart_app.Items.ItemRepository;
import groKart_app.Users.User;
import groKart_app.Users.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="StoreController", description = "REST APIs for the entire Store class controllers")
@RestController
public class StoreController {
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * GET ALL STORES IN THE DB
     *
     * @return
     */
    @ApiOperation(value="Get List of Stores in Database", response=Iterable.class, tags="StoreController")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Success|OK"),
            @ApiResponse(code=401, message = "Not Authorized"),
            @ApiResponse(code=403, message = "Forbidden!"),
            @ApiResponse(code=404, message = "Error!"),
            @ApiResponse(code=500, message = "Server Not Found")
    })
    @GetMapping(path = "/stores")
    List<Store> getAllStores(){
        return storeRepository.findAll();
    }


    /**
     * GET A SPECIFIC STORE
     * @param storeName
     * @return
     */
    @ApiOperation(value="Get Specific Store by Store Name from Database", response=Iterable.class, tags="StoreController")
    @GetMapping(path = "/stores/{storeName}")
    Store getSpecificStore(@PathVariable String storeName){
        Store store = storeRepository.findByStoreName(storeName);
        return store;
    }

    /**
     * POST A STORE
     * @param store
     * @return
     */
    @ApiOperation(value="Post a new Store", response=Iterable.class, tags="StoreController")
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
     */
    @ApiOperation(value="Get All the Items of a Specific Store", response=Iterable.class, tags="StoreController")
    @GetMapping(path ="/stores/{storeName}/items")
    List<Item> returnStoreItems(@PathVariable String storeName){
        Store store = storeRepository.findByStoreName(storeName);
        return store.getItems();
    }

    /**
     * GET count of users with a particular preferred store
     *
     */
    @GetMapping(path = "/stores/{storeName}/countUsers")
    String getNumUsersByStore(@PathVariable String storeName) {
        Store store = storeRepository.findByStoreName(storeName);
        if (store == null) return "{\"message\":\"Store does not exist.\"}";
        List<User> users = userRepository.findAllByPreferredStore((storeName));
        int count = users.size();
        return "{\"numUsers\":\"" + count + "\"}";
    }

    /**
     * UPDATE STORE
     * @param storeName
     * @param storeRequest
     * @return
     */
    @ApiOperation(value="Update a Specific Store", response=Iterable.class, tags="StoreController")
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
     * @param newItem
     * @return
     */
    @ApiOperation(value="Assign or Add an Item to a Specific Store", response=Iterable.class, tags="StoreController")
    @PutMapping(path = "/stores/items")
    String addItemToStore(@RequestBody Item newItem){
        if(newItem == null){return failure;}
        Store store = storeRepository.findByStoreName(newItem.getStoreName());
        if(store == null ||itemRepository.existsByStoreNameAndName(newItem.getStoreName(),newItem.getName())){return failure;}
        newItem.setStore(store);
        itemRepository.save(newItem);
        storeRepository.save(store);
        return success;
    }


    /**
     * REMOVE STORE
     * @param storeName
     * @return
     */
    @ApiOperation(value="Remove a Store from the Database", response=Iterable.class, tags="StoreController")
    @DeleteMapping(path = "/stores/{storeName}")
    String deleteStore(@PathVariable String storeName){
        storeRepository.deleteByStoreName(storeName);
        return success;
    }
}




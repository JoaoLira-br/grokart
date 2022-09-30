package onetoone.Stores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Users.User;
import onetoone.Users.UserRepository;

@RestController
public class StoreController {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/stores")
    List<Store> getAllStores(){
        return storeRepository.findAll();
    }

    @GetMapping(path = "/stores/{id}")
    Store getStoreById(@PathVariable int id){
        return storeRepository.findById(id);
    }

    @PostMapping(path = "/stores")
    String createStore(@RequestBody Store Store){
        if (Store == null)
            return failure;
        storeRepository.save(Store);
        return success;
    }

    @PutMapping(path = "/stores/{id}")
    Store updateStore(@PathVariable int id, @RequestBody Store request){
        Store store = storeRepository.findById(id);
        if(store == null)
            return null;
        storeRepository.save(request);
        return storeRepository.findById(id);
    }

    @DeleteMapping(path = "/stores/{id}")
    String deleteStore(@PathVariable int id){

        // Check if there is an object depending on user and then remove the dependency
        User user = userRepository.findByStore_Id(id);
        user.setStore(null);
        userRepository.save(user);

        // delete the store if the changes have not been reflected by the above statement
        storeRepository.deleteById(id);
        return success;
    }
}

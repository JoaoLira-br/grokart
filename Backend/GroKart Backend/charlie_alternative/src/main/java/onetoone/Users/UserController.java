package onetoone.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Stores.Store;
import onetoone.Stores.StoreRepository;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoreRepository storeRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){
        return userRepository.findById(id);
    }

    // LOGIN
    @GetMapping(path = "/users/{userName}/{password}")
    String getUserById( @PathVariable String userName, @PathVariable String password) {
        User user = userRepository.findByUserName(userName);
        if (user.getPassword().equals(password)) return "Successful login";
        else return "Failed login.";
    }

    // CREATE USER
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request){
        User user = userRepository.findById(id);
        if(user == null)
            return null;
        userRepository.save(request);
        return userRepository.findById(id);
    }   
    
    @PutMapping("/users/{userId}/stores/{storeId}")
    String assignStoreToUser(@PathVariable int userId,@PathVariable int storeId){
        User user = userRepository.findById(userId);
        Store store = storeRepository.findById(storeId);
        if(user == null || store == null)
            return failure;
        store.setUser(user);
        user.setStore(store);
        userRepository.save(user);
        return success;
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return success;
    }
}
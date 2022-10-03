package groKart_app.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    StoreRepository storeRepository;

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
//        if (user.getPassword().equals(password)) return "Successful login";
//        else return "Failed login.";

        if (user == null || !user.getPassword().equals(password)) return "Failed login";
        else return "Successful login";
    }

    // CREATE USER
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null || userRepository.existsByUserName(user.getUserName()))
            return failure;
        userRepository.save(user);
        return success;
    }

    @PutMapping("/users/{userName}")
    User updateUser(@PathVariable String userName, @RequestBody User request){
        User user = userRepository.findByUserName(userName);
        if(user == null || userRepository.existsByUserName(user.getUserName()))
            return null;
        userRepository.save(request);
        return userRepository.findByUserName(userName);
    }   

    @DeleteMapping(path = "/users/{userName}")
    String deleteUser(@PathVariable String userName){
        userRepository.deleteByUserName(userName);
        return success;
    }
}

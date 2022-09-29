package groKartApp.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    private String requestSuccess = "{\"message\":\"success\"}";
    private String requestFailure = "{\"message\":\"failure\"}";

    //USERS
    @GetMapping(path = "/users")
    List<User> getUsersLists(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/user/{userID}/{password}")
    User getSpecificUser(@PathVariable int userID, String password){
        return userRepository.findUsingId(userID, password);
    }

    @PostMapping(path = "/user")
    String createUser(@RequestBody User user) {
        if (user == null) {return requestFailure;}
        userRepository.save(user);
        return requestSuccess;
    }

    @DeleteMapping(path = "/user/{userID}")
    String deleteSpecificUser(@PathVariable int userID){
        userRepository.deleteUsingId(userID);
        return requestSuccess;
    }






}

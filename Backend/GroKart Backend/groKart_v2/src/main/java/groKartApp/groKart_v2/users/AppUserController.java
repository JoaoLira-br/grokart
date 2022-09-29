package groKartApp.groKart_v2.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {
    @Autowired
    AppUserRepository appUserRepository;

    private String requestSuccess = "{\"message\":\"success\"}";
    private String requestFailure = "{\"message\":\"failure\"}";

    //USERS
    @GetMapping(path = "/users")
    List<AppUser> getUsersLists(){
        return appUserRepository.findAll();
    }

    @GetMapping(path = "/user/{userID}/{password}")
    AppUser getSpecificUser(@PathVariable int userID, String password){
        return appUserRepository.findUsingId(userID, password);
    }

    @PostMapping(path = "/user")
    String createUser(@RequestBody AppUser appUser) {
        if (appUser == null) {return requestFailure;}
        appUserRepository.save(appUser);
        return requestSuccess;
    }

    @DeleteMapping(path = "/user/{userID}")
    String deleteSpecificUser(@PathVariable int userID){
        appUserRepository.deleteUsingId(userID);
        return requestSuccess;
    }

}

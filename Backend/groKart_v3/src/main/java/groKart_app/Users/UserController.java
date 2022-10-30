package groKart_app.Users;

import java.util.List;

import groKart_app.Karts.Kart;
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

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * GET ALL USERS IN THE DB
     * @return
     */
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * SEARCH USER BY USERNAME
     * Used for profile update search after login
     * @param userName
     * @return
     */
    @GetMapping(path = "/user/{userName}")
    User getSpecificUser( @PathVariable String userName){
        return userRepository.findByUserName(userName);
    }

    /**
     * SEARCH USER BY ID
     * @param id
     * @return
     */
    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){
        return userRepository.findById(id);
    }

    /**
     * LOGIN
     * @param userName
     * @param password
     * @return
     */
    @GetMapping(path = "/users/{userName}/{password}")
    String getUserById( @PathVariable String userName, @PathVariable String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);

        if (user == null) return failure;
        else return success;
    }

    /**
     * CREATE USER
     * @param user
     * @return
     */
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null || userRepository.existsByUserName(user.getUserName()))
            return failure;
        userRepository.save(user);
        return success;
    }

    /**
     * UPDATE USER
     * @param userName
     * @param request
     * @return
     */
    @PutMapping("/users/{userName}")
    User updateUser(@PathVariable String userName, @RequestBody User request){
        User user = userRepository.findByUserName(userName);
        if (user == null)
            return null;
        userRepository.deleteByUserName((userName));
        userRepository.save(request);
        return userRepository.findByUserName(userName);
    }

    /**
     * UPDATE DISPLAY NAME OF USER
     * @param userName
     * @return
     */
    @PutMapping("/displayName/{userName}")
    String updateDisplayName(@PathVariable String userName, @RequestBody String displayName){
        User user = userRepository.findByUserName(userName);
        if (user == null)
            return failure;
        user.setDisplayName(displayName);
        userRepository.save(user);
        return success;
    }

    /**TODO preferred Store update 
     * UPDATE PASSWORD OF USER
     * @param userName
     * @return
     */
    @PutMapping("/password/{userName}")
    String updatePassword(@PathVariable String userName, @RequestBody String newPassword){
        User user = userRepository.findByUserName(userName);
        if (user == null)
            return failure;
        user.setPassword(newPassword);
        userRepository.save(user);
        return success;
    }


    /**
     * DELETE USER
     * @param userName
     * @return
     */
    @DeleteMapping(path = "/users/{userName}")
    String deleteUser(@PathVariable String userName){
        userRepository.deleteByUserName(userName);
        return success;
    }

    /**
     * GET OWNED KARTS - karts that user can edit
     */
    @GetMapping(path = "/users/ownedKarts/{userName}")
    List<Kart> getOwnedKarts(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName);
        return user.getOwnedKarts();
    }

    /**
     * GET ALL VISIBLE KARTS
     */
    @GetMapping(path = "/users/allKarts/{userName}")
    List<Kart> getAllKarts(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName);
        List<Kart> karts = user.getOwnedKarts();
        karts.addAll(user.getOwnedKarts());
        return karts;
    }

    /**
     * GET FRIENDS
     */
    @GetMapping(path = "/users/friends/{userName}")
    List<User> getFriends(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName);
        return user.getFriends();
    }

    /**
     * ADD FRIEND
     */
    @PutMapping(path = "/users/friend/{userName1}/{userName2}")
    void makeFriends(@PathVariable String userName1, @PathVariable String userName2) {
        User user1 = userRepository.findByUserName(userName1);
        User user2 = userRepository.findByUserName(userName2);
        user1.addFriend(user2);
        user2.addFriend(user1);

        userRepository.save(user1);
        userRepository.save(user2);
    }

    /**
     * REMOVE FRIEND
     */
    @PutMapping(path = "/users/unfriend/{userName1}/{userName2}")
    void unfriend(@PathVariable String userName1, @PathVariable String userName2) {
        User user1 = userRepository.findByUserName(userName1);
        User user2 = userRepository.findByUserName(userName2);
        user1.removeFriend(user2);
        user2.removeFriend(user1);

        userRepository.save(user1);
        userRepository.save(user2);
    }
}

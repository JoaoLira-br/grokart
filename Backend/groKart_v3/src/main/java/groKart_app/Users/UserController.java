package groKart_app.Users;

import java.util.ArrayList;
import java.util.List;

import groKart_app.Karts.Kart;
import groKart_app.Stores.Store;
import groKart_app.Stores.StoreRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="UserController", description = "REST APIs for the entire User class controllers")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoreRepository storeRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * GET ALL USERS IN THE DB
     * @return
     */
    @ApiOperation(value="Get List of App User in Database", response=Iterable.class, tags="UserController")
    @ApiResponses(value = {
           @ApiResponse(code=200, message = "Success|OK"),
            @ApiResponse(code=401, message = "Not Authorized"),
            @ApiResponse(code=403, message = "Forbidden!"),
            @ApiResponse(code=404, message = "Error!"),
            @ApiResponse(code=500, message = "Server Not Found")
    })
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
    @ApiOperation(value="Get User by Username from Database", response=Iterable.class, tags="UserController")
    @GetMapping(path = "/user/{userName}")
    User getSpecificUser( @PathVariable String userName){
        return userRepository.findByUserName(userName);
    }

    /**
     * SEARCH USER BY ID
     * @param id
     * @return
     */
    @ApiOperation(value="Get User by ID from Database", response=Iterable.class, tags="UserController")
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
    @ApiOperation(value="Login Path for App User", response=Iterable.class, tags="UserController")
    @GetMapping(path = "/users/{userName}/{password}")
    String getUserForLogin( @PathVariable String userName, @PathVariable String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);
        if (user == null) return "{\"privilege\":\"-1\"}";
        else return "{\"privilege\":\"" + user.getPrivilege() + "\"}";
    }

    /**
     * CREATE USER
     * @param user
     * @return
     */
    @ApiOperation(value="Create an App User", response=Iterable.class, tags="UserController")
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null || userRepository.existsByUserName(user.getUserName()))
            return failure;
        userRepository.save(user);
        return success;
    }

    /**
     * UPDATE DISPLAY NAME OF USER
     * @param userName
     * @return
     */
    @ApiOperation(value="Update Display Name of App User", response=Iterable.class, tags="UserController")
    @PutMapping("/displayName/{userName}/{displayName}")
    String updateDisplayName(@PathVariable String userName, @PathVariable String displayName){
        User user = userRepository.findByUserName(userName);
        if (user == null)
            return failure;
        user.setDisplayName(displayName);
        userRepository.save(user);
        return success;
    }

    /**
     * UPDATE PASSWORD OF USER
     * @param userName
     * @return
     */
    @ApiOperation(value="Update App User Password", response=Iterable.class, tags="UserController")
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
     * UPDATE PREFERRED STORE OF USER
     * @param userName
     * @return
     */
    @ApiOperation(value="Update App User's Preferred Store", response=Iterable.class, tags="UserController")
    @PutMapping("/preferredStore/{userName}/{preferredStore}")
    String updatePreferredStore(@PathVariable String userName, @PathVariable String preferredStore){
        User user = userRepository.findByUserName(userName);
        if (user == null) return "{\"message\":\"User does not exist.\"}";
        Store store = storeRepository.findByStoreName(preferredStore);
        if (store == null) return "{\"message\":\"Store does not exist.\"}";
        user.setPreferredStore(preferredStore);
        userRepository.save(user);
        return success;
    }

    /**
     * UPDATE EMAIL OF USER
     * @param userName
     * @return
     */
    @ApiOperation(value="Email Address App User's Preferred Store", response=Iterable.class, tags="UserController")
    @PutMapping("/emailAdd/{userName}/{emailAdd}")
    String updateEmailAdd(@PathVariable String userName, @PathVariable String emailAdd){
        User user = userRepository.findByUserName(userName);
        if (user == null)
            return failure;
        user.setEmailAdd(emailAdd);
        userRepository.save(user);
        return success;
    }

    /**
     * DELETE USER
     * @param userName
     * @return
     */
    @ApiOperation(value="Delete an App User using Username", response=Iterable.class, tags="UserController")
    @DeleteMapping(path = "/users/{userName}")
    String deleteUser(@PathVariable String userName){
        userRepository.deleteByUserName(userName);
        return success;
    }

    /**
     * GET PREFERRED STORE FOR USER
     * @param userName
     * @return
     */
    @ApiOperation(value="Get App User's Preferred Store", response=Iterable.class, tags="UserController")
    @GetMapping(path = "/user/{userName}/preferredStore")
    String getPreferredStore( @PathVariable String userName){
        User user = userRepository.findByUserName(userName);

        return "{\"storeName\":\"" + user.getPreferredStore() + "\"}";
    }

    /**
     * GET OWNED KARTS - karts that user can edit
     */
    @ApiOperation(value="Get App User's All Owned Karts", response=Iterable.class, tags="UserController")
    @GetMapping(path = "/users/ownedKarts/{userName}")
    List<Kart> getOwnedKarts(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName);
        return user.getOwnedKarts();
    }

    /**
     * GET ALL FRIEND'S KARTS
     */
    @ApiOperation(value="Get App User's All Friend's Karts", response=Iterable.class, tags="UserController")
    @GetMapping(path = "/users/friendsKarts/{userName}")
    List<Kart> getAllFriendsKarts(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName);

        List<Kart> karts = new ArrayList<Kart>();

        List<User> users = user.getFriends();

        for (User friend : users) {
            for (Kart k : friend.getOwnedKarts()) {
//                if (k.getPublicity()) {
                    karts.add(k);
//                }
            }
        }

        return karts;
    }

    /**
     * GET ALL VISIBLE KARTS
     */
    @ApiOperation(value="Get App User's All Visible Karts", response=Iterable.class, tags="UserController")
    @GetMapping(path = "/users/allKarts/{userName}")
    List<Kart> getAllKarts(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName);

        List<Kart> karts = user.getOwnedKarts();

        List<User> users = user.getFriends();

        for (User friend : users) {
            for (Kart k : friend.getOwnedKarts()) {
                karts.add(k);
            }
        }

        return karts;
    }

    /**
     * GET FRIENDS
     */
    @ApiOperation(value="Get App User's Friends List", response=Iterable.class, tags="UserController")
    @GetMapping(path = "/users/friends/{userName}")
    List<User> getFriends(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName);
        return user.getFriends();
    }

    /**
     * ADD FRIEND
     */
    @ApiOperation(value="Add a friend to an App User using their Username", response=Iterable.class, tags="UserController")
    @PutMapping(path = "/users/friend/{userName1}/{userName2}")
    int makeFriends(@PathVariable String userName1, @PathVariable String userName2) {
        User user1 = userRepository.findByUserName(userName1);
        User user2 = userRepository.findByUserName(userName2);
        if (user1 == null || user2 == null) return 1;
        user1.addFriend(user2);
        user2.addFriend(user1);

        userRepository.save(user1);
        userRepository.save(user2);
        return 0;
    }

    /**
     * REMOVE FRIEND
     */
    @ApiOperation(value="Remove an App User's Friend", response=Iterable.class, tags="UserController")
    @PutMapping(path = "/users/unfriend/{userName1}/{userName2}")
    int unfriend(@PathVariable String userName1, @PathVariable String userName2) {
        User user1 = userRepository.findByUserName(userName1);
        User user2 = userRepository.findByUserName(userName2);
        user1.removeFriend(user2);
        user2.removeFriend(user1);

        userRepository.save(user1);
        userRepository.save(user2);
        return 0;
    }

    /**
     * Create App administrator - request should only be sent when the app admin is logged in
     * @param storeName
     * @param username
     * @param password
     * @return
     */
    @PostMapping(path = "/appAdmin/createStoreAdmin/{storeName}/{username}/{password}")
    String createStoreAdmin(@PathVariable String storeName, @PathVariable String username, @PathVariable String password) {
        Store store = storeRepository.findByStoreName(storeName);
        if (store == null) return "{\"error\":\"Store " + storeName + " does not exist\"}";
        User user = userRepository.findByUserName(username);
        if (user != null) return "{\"error\":\"User " + username + " already exists\"}";

        user = new User();
        user.setUserName(username);
        user.setPreferredStore(storeName);
        user.setPassword(password);
        user.setPrivilege(1);
        user.setDisplayName(storeName + " Administrator");

        userRepository.save(user);

        return success;
    }

    /**
     * GET all base users
     * @return
     */
    @GetMapping(path = "baseUsers")
    List<User> getBaseUsers() {
        return userRepository.findAllByPrivilege(0);
    }

    /**
     * GET all store administrators
     * @return
     */
    @GetMapping(path = "storeAdmins")
    List<User> getStoreAdmins() {
        return userRepository.findAllByPrivilege(1);
    }


}

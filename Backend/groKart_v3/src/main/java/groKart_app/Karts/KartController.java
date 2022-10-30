package groKart_app.Karts;

import groKart_app.Items.Item;
import groKart_app.Items.ItemRepository;
import groKart_app.Users.User;
import groKart_app.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KartController {
    @Autowired
    KartRepository kartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    /**
     * GET ALL KARTS IN THE DB
     *
     * @return
     */
    @GetMapping(path = "/karts")
    List<Kart> getAllKarts() {
        return kartRepository.findAll();
    }

    /**
     * Create new Kart -- Requires entire Kart passed as JSON object
     * @param kart
     * @return
     */
    @PostMapping(path = "/karts")
    int createKart(@RequestBody Kart kart){
        // error: kart with this name already exists
        // if user owns the kart, they could overwrite it
        if(kart == null || kartRepository.existsByKartName(kart.getName())) return 0;

        kartRepository.save(kart);
        return 0;
    }
    /**
     * Create new Kart -- Requires only the name of the kart. This alternative might be useful
     *  if the frontend would find it easier to create an empty kart and then add items to it
     *  rather than creating an entire kart with a list of items.
     *
     *  Using a GET req to Create an empty Kart
     *
     * @param kartName
     * @return
     */
    @GetMapping(path = "/karts/{userName}/{kartName}")
    int createKart(@PathVariable String userName, @PathVariable String kartName){
        // error: kart with this name already exists
        if(kartRepository.existsByKartName(kartName)) return 1;
        if(!userRepository.existsByUserName(userName)) return 1;
        Kart kart = new Kart(kartName);
        kart.setOwner(userRepository.findByUserName(userName));
        kartRepository.save(kart);
        return 0;
    }

    // Karts currently are all public, cannot have duplicate names
    @DeleteMapping(path = "/karts/{kartName}")
    int deleteKart(@PathVariable String kartName) {
        Kart kart = kartRepository.findByKartName(kartName);
        kart.getOwner().removeKart(kart);
        kartRepository.deleteByKartName(kartName);
        return 0;
    }

    /**
     * ADD ITEM TO KART
     * @param kartName
     * @param storeName
     * @param itemName
     * @return
     */
    @PutMapping(path = "karts/addItem/{kartName}/{storeName}/{itemName}")
    String addItemToKart(@PathVariable String kartName, @PathVariable String storeName, @PathVariable String itemName) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        Kart kart = kartRepository.findByKartName(kartName);

        if (kart.getItems().contains(item)) return "kart already contains item";

        kart.addItem(item);
        kartRepository.save(kart);
        item.addKart(kart);
        itemRepository.save(item);
        return item + "\n" + kart;
    }

    /**
     * REMOVE ITEM TO KART
     * @param kartName
     * @param storeName
     * @param itemName
     * @return
     */
    @PutMapping(path = "karts/removeItem/{kartName}/{storeName}/{itemName}")
    String removeItemFromKart(@PathVariable String kartName, @PathVariable String storeName, @PathVariable String itemName) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        Kart kart = kartRepository.findByKartName(kartName);

        kart.removeItem(item);
        kartRepository.save(kart);
        item.removeKart(kart);
        itemRepository.save(item);
        return item + "\n" + kart;
    }

    /**
     * ADD USER TO KART (makes them co-owner)
     * @param kartName
     * @param userName
     * @return
     */
    @PutMapping(path = "karts/addUser/{kartName}/{userName}")
    String userFollowKart(@PathVariable String kartName, @PathVariable String userName) {
        User user = userRepository.findByUserName(userName);
        Kart kart = kartRepository.findByKartName(kartName);

        if (kart.getUsers().contains(user)) return "user already follows list";

        kart.addUser(user);
        kartRepository.save(kart);
        user.addKart(kart);
        userRepository.save(user);
        return user + "\n" + kart;
    }

    /**
     * REMOVE USER FROM KART
     * @param kartName
     * @param userName
     * @return
     */
    @PutMapping(path = "karts/removeUser/{kartName}/{userName}")
    String userUnFollowKart(@PathVariable String kartName, @PathVariable String userName) {
        User user = userRepository.findByUserName(userName);
        Kart kart = kartRepository.findByKartName(kartName);

        kart.removeUser(user);
        kartRepository.save(kart);
        user.removeKart(kart);
        userRepository.save(user);
        return user + "\n" + kart;
    }


    /**
     * karts/friendsof/{userName}
     * returns all friends' carts to populate feed
     */

}

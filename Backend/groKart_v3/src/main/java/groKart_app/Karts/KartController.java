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
     * Create new Kart
     * @param kart
     * @return
     */
    @PostMapping(path = "/karts")
    int createKart(@RequestBody Kart kart){
        if(kart == null || kartRepository.existsByKartName(kart.getName()))
            return 0;
        kartRepository.save(kart);
        return 1;
    }

    // Karts currently are all public, cannot have duplicate names
    @DeleteMapping(path = "/karts/{kartName}")
    int deleteKart(@PathVariable String kartName) {
        kartRepository.deleteByKartName(kartName);
        return 1;
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
    @PutMapping(path = "karts/addUser/{kartName}/{userName")
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



}

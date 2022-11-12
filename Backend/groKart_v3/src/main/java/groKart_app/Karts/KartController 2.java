package groKart_app.Karts;

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

@Api(value="KartController", description = "REST APIs for the entire Kart class controllers")
@RestController
public class KartController {
    @Autowired
    KartRepository kartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    private String failure = "{\"message\":\"failure\"}";

    /**
     * GET ALL KARTS IN THE DB
     *
     * @return
     */
    @ApiOperation(value="Get List of All Karts in Database", response=Iterable.class, tags="KartController")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Success|OK"),
            @ApiResponse(code=401, message = "Not Authorized"),
            @ApiResponse(code=403, message = "Forbidden!"),
            @ApiResponse(code=404, message = "Error!"),
            @ApiResponse(code=500, message = "Server Not Found")
    })
    @GetMapping(path = "/karts")
    List<Kart> getAllKarts() {
        return kartRepository.findAll();
    }

    /**
     * Create new Kart -- Requires only the name of the kart. This alternative might be useful
     *  if the frontend would find it easier to create an empty kart and then add items to it
     *  rather than creating an entire kart with a list of items.
     *
     * @param kartName
     * @return
     */
    @ApiOperation(value="Create New Kart", response=Iterable.class, tags="KartController")
    @PostMapping(path = "/karts/{userName}/{kartName}")
    int createKart(@PathVariable String userName, @PathVariable String kartName){
        // error: kart with this name already exists
        if(kartRepository.existsByKartName(kartName)) return 1;
        if(!userRepository.existsByUserName(userName)) return 1;
        Kart kart = new Kart(kartName);
        kart.setOwner(userRepository.findByUserName(userName));
        kartRepository.save(kart);
        return 0;
    }

    @ApiOperation(value="Delete A Kart", response=Iterable.class, tags="KartController")
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
    @ApiOperation(value="Add an Item and Quantity to the Kart", response=Iterable.class, tags="KartController")
    @PutMapping(path = "karts/addItem/{kartName}/{storeName}/{itemName}/{quantity}")
    String addItemToKart(@PathVariable String kartName, @PathVariable String storeName, @PathVariable String itemName, @PathVariable int quantity) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        Kart kart = kartRepository.findByKartName(kartName);

        if (item == null || kart == null) return failure;

//        if (kart.getItems().contains(item)) return "{\"message\":\"kart already contains item\"}";

        if (!kart.getItems().contains(item)) {
            kart.addItem(item);
            item.addKart(kart);
        }
        kart.setQuantity(item, new Integer(quantity));
        kartRepository.save(kart);
        itemRepository.save(item);
        return item + "\n" + kart;
    }

    /**
     * REMOVE ITEM FROM KART
     * @param kartName
     * @param storeName
     * @param itemName
     * @return
     */
    @ApiOperation(value="Remove An Item from Kart", response=Iterable.class, tags="KartController")
    @PutMapping(path = "karts/removeItem/{kartName}/{storeName}/{itemName}")
    String removeItemFromKart(@PathVariable String kartName, @PathVariable String storeName, @PathVariable String itemName) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        Kart kart = kartRepository.findByKartName(kartName);

        if (item == null || kart == null) return failure;

        kart.removeItem(item);
        kartRepository.save(kart);
        item.removeKart(kart);
        itemRepository.save(item);
        return item + "\n" + kart;
    }

    @ApiOperation(value="Get Total Price of a Kart", response=Iterable.class, tags="KartController")
    @GetMapping(path = "karts/total/{kartName}")
    double getTotal(@PathVariable String kartName) {
        Kart kart = kartRepository.findByKartName(kartName);
        return kart.getTotalPrice();
    }
    /**
     * SET PUBLICITY
     * @param kartName
     * @param storeName
     * @param publicity
     * @return
     */
    @ApiOperation(value="Set A Publicity for Kart", response=Iterable.class, tags="KartController")
    @PutMapping(path = "karts/setPublicity/{kartName}/{storeName}/{publicity}")
    String setPublicity(@PathVariable String kartName, @PathVariable String storeName, @PathVariable boolean publicity) {
        Kart kart = kartRepository.findByKartName(kartName);

        if (kart == null) return failure;

        kart.setPublicity(publicity);
        kartRepository.save(kart);
        return "" + kart;
    }
}

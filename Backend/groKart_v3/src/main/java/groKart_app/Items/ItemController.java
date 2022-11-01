package groKart_app.Items;

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
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * GET Items
     * @return
     */
    @GetMapping(path = "/items")
    List<Item> getAllItems() { return itemRepository.findAll(); }

    /**
     * GET ITEM (by name & store)
     * @param itemName
     * @return
     */
    @GetMapping(path = "/items/{storeName}/{itemName}")
    Item getItemByName(@PathVariable String storeName, @PathVariable String itemName) {
        return itemRepository.findByStoreNameAndName(storeName, itemName);
    }

    /**
     * GET ITEMS OF STORE
     * @param storeName
     * @return
     */
    @GetMapping(path = "/items/{storeName}")
    List<Item> getItemStore(@PathVariable String storeName) {
        return itemRepository.findAllByStoreName(storeName);
    }

    /**
     * CREATE ITEM
     *  if item already exists with item.name
     *      error (might consider treating it like a PUT and updating item with info)
     * @param item
     * @return
     */
    @PostMapping(path = "/items")
    String createItem(@RequestBody Item item) {
        if (item == null || itemRepository.existsByStoreNameAndName(item.getStoreName(), item.getName()))
            return failure;
        itemRepository.save(item);
        return success;
    }

    /**
     * UPDATE ITEM NAME
     * @param storeName
     * @param itemName
     * @param newName
     * @return
     */
    @PutMapping(path = "/items/updateName/{storeName}/{itemName}/{newName}")
    Item updateItemName(@PathVariable String storeName, @PathVariable String itemName, @PathVariable String newName) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        if (item == null)
            return null;
        item.setName(newName);
        itemRepository.save(item);
        return item;
    }

    /**
     * UPDATE ITEM PRICE
     * @param storeName
     * @param itemName
     * @param newPrice
     * @return
     */
    @PutMapping(path = "/items/updatePrice/{storeName}/{itemName}/{newPrice}")
    Item updateItemPrice(@PathVariable String storeName, @PathVariable String itemName, @PathVariable double newPrice) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        if (item == null)
            return null;
        item.setPrice(newPrice);
        itemRepository.save(item);
        return item;
    }

    /**
     * UPDATE ITEM STORE
     * @param storeName
     * @param itemName
     * @param newStoreName
     * @return
     */
    @PutMapping(path = "/items/updateStoreName/{storeName}/{itemName}/{newStoreName}")
    Item updateItemStore(@PathVariable String storeName, @PathVariable String itemName, @PathVariable String newStoreName) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        if (item == null)
            return null;
        item.setStoreName(newStoreName);
        itemRepository.save(item);
        return item;
    }

    /**
     * UPDATE ITEM QUANTITY
     * @param storeName
     * @param itemName
     * @param newQuantity
     * @return
     */
    @PutMapping(path = "/items/updateQuantity/{storeName}/{itemName}/{newQuantity}")
    Item updateItemQuantity(@PathVariable String storeName, @PathVariable String itemName, @PathVariable int newQuantity) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        if (item == null)
            return null;
        item.setQuantity(newQuantity);
        itemRepository.save(item);
        return item;
    }

    /**
     * DELETE ITEM
     * @param itemName
     * @return
     */
    @DeleteMapping(path = "/items/{storeName}/{itemName}")
    String deleteItem(@PathVariable String storeName, @PathVariable String itemName) {
        itemRepository.deleteByStoreNameAndName(storeName, itemName);
        return success;
    }
}



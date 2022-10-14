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
     * GET ITEMS
     * @return
     */
    @GetMapping(path = "/items")
    List<Item> getAllItems() { return itemRepository.findAll(); }

    /**
     * GET ITEMS (by name) --returns the items (all duplicate ones if all different stores)
     * @param itemName
     * @return
     */
    @GetMapping(path = "/items/{itemName}")
    Item getItemByName(@PathVariable String itemName) {
        return itemRepository.findByName(itemName);
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
     * UPDATE ITEM
     * @param itemName
     * @param request
     * @return
     */
    @PutMapping(path = "/items/{storeName}/{itemName}")
    Item updateItem(@PathVariable String storeName, @PathVariable String itemName, @RequestBody Item request) {
        Item item = itemRepository.findByStoreNameAndName(storeName, itemName);
        if (item == null)
            return null;
        itemRepository.deleteByStoreNameAndName(storeName, itemName);
        itemRepository.save(request);
        return itemRepository.findByStoreNameAndName(storeName, itemName);
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



package groKart_app.Items;

import java.util.List;

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

@Api(value="ItemController", description = "REST APIs for the entire Item class controllers")
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
    @ApiOperation(value="Get List of All Items in Database", response=Iterable.class, tags="ItemController")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Success|OK"),
            @ApiResponse(code=401, message = "Not Authorized"),
            @ApiResponse(code=403, message = "Forbidden!"),
            @ApiResponse(code=404, message = "Error!"),
            @ApiResponse(code=500, message = "Server Not Found")
    })
    @GetMapping(path = "/items")
    List<Item> getAllItems() { return itemRepository.findAll(); }

    /**
     * GET ITEM (by name & store)
     * @param itemName
     * @return
     */
    @ApiOperation(value="Get A Specific Item with StoreName", response=Iterable.class, tags="ItemController")
    @GetMapping(path = "/items/{storeName}/{itemName}")
    Item getItemByName(@PathVariable String storeName, @PathVariable String itemName) {
        return itemRepository.findByStoreNameAndName(storeName, itemName);
    }

    /**
     * GET ITEMS OF STORE
     * @param storeName
     * @return
     */
    @ApiOperation(value="Get List of All Items in a Specific Store", response=Iterable.class, tags="ItemController")
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
    @ApiOperation(value="Create a New Item", response=Iterable.class, tags="ItemController")
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
    @ApiOperation(value="Update Item Name", response=Iterable.class, tags="ItemController")
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
    @ApiOperation(value="Update Item Price", response=Iterable.class, tags="ItemController")
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
    @ApiOperation(value="Update Store that Item attached to", response=Iterable.class, tags="ItemController")
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
    @ApiOperation(value="Update Item Quantity", response=Iterable.class, tags="ItemController")
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
    @ApiOperation(value="Delete an Item", response=Iterable.class, tags="ItemController")
    @DeleteMapping(path = "/items/{storeName}/{itemName}")
    String deleteItem(@PathVariable String storeName, @PathVariable String itemName) {
        itemRepository.deleteByStoreNameAndName(storeName, itemName);
        return success;
    }
}



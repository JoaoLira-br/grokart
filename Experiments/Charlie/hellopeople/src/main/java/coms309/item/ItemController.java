package coms309.item;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class ItemController {

    // Note that there is only ONE instance of itemController in
    // Springboot system.
    HashMap<String, Item> itemList = new  HashMap<>();

    ArrayList<String> names = new ArrayList<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the item in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/item")
    public @ResponseBody HashMap<String, Item> getAllItems() {
        return itemList;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a Item object and
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/item")
    public @ResponseBody String createItem(@RequestBody Item Item) {
        System.out.println(Item);
        itemList.put(Item.getName(), Item);
        names.add(Item.getName());
        return "New Item "+ Item.getName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the Item from the HashMap.
    // springboot automatically converts Item to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/item/{name}")
    public @ResponseBody Item getItem(@PathVariable String name) {
        Item i = itemList.get(name);
        return i;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the Item from the HashMap and modify it.
    // Springboot automatically converts the Item to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/item/{name}")
    public @ResponseBody Item updateItem(@PathVariable String name, @RequestBody Item i) {
        itemList.replace(name, i);
        return itemList.get(name);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/item/{name}")
    public @ResponseBody HashMap<String, Item> deleteItem(@PathVariable String name) {
        itemList.remove(name);
        names.remove(name);
        return itemList;
    }

    // THIS IS A GET OPERATION I CREATED
    // We return all items which have the highest rating
    @GetMapping("/item/top")
    public @ResponseBody ArrayList<Item> topItem() {
        if (itemList.isEmpty()) return null;
        ArrayList<Item> topRated = new ArrayList<>();
        double maxRating = 0;
        for (String str : names) {
            Item i = itemList.get(str);
            if (i.getRating() > maxRating) {
                topRated.clear();
                topRated.add(i);
                maxRating = i.getRating();
            } else if (i.getRating() == maxRating) {
                topRated.add(i);
            }
        }
        return topRated;
    }
}


package coms309.people;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.awt.Desktop;
import coms309.groceries.Grocery;
/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class PeopleController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, Person> peopleList = new  HashMap<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/peopleList")
    public @ResponseBody HashMap<String,Person> getAllPersons() {
        return peopleList;
    }

    @GetMapping("/peopleList/{lastName}")
    public @ResponseBody Person getPersonLast(@PathVariable String lastName){
        Person p = peopleList.get(lastName);
        return p;
    }
    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public @ResponseBody String createPerson(@RequestBody Person person) {
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/people/{firstName}")
    public @ResponseBody Person getPerson(@PathVariable String firstName) {
        Person p = peopleList.get(firstName);
        return p;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{firstName}")
    public @ResponseBody Person updatePerson(@PathVariable String firstName, @RequestBody Person p) {
        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/people/{firstName}")
    public @ResponseBody HashMap<String, Person> deletePerson(@PathVariable String firstName) {
        peopleList.remove(firstName);
        return peopleList;
    }

    //For equational purposes
    @GetMapping ("/equation/{numbers}")
    public @ResponseBody String doEquation(@PathVariable String numbers){
        int result = Integer.parseInt(numbers)*100/12;
        return "Your number is :" + result;
    }





    //For Groceries
    @GetMapping("/groceries/{firstName}")
    public @ResponseBody Grocery getGroceries(@PathVariable String firstName) {
        Grocery g = peopleList.get(firstName).getItems();
        return g;
    }

    //For put Items - doesn't work
    @PutMapping("/groceries/{firstname}")
    public @ResponseBody Person updateItems(@PathVariable String firstName, @RequestBody Grocery items){
        peopleList.get(firstName).setItems(items);
        return peopleList.get(firstName);
    }


    // For file loading - still working on this - FOUND THAT NEED MAIN FOR EXECUTION
    @GetMapping("/loadingFile")
    public @ResponseBody String loadingFile() throws IOException {
        File file = new File("/Users/bagab/Desktop/hello.txt");
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()){
            desktop.open(file);
        }
        return "File successfully loaded";
    }
}


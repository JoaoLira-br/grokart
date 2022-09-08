package coms309.people;


import coms309.groceries.Grocery;

/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class  Person {

    private String firstName;

    private String lastName;

    private String address;

    private String telephone;

    private Grocery items;

    public Person(){
        
    }

    public Person(String firstName, String lastName, String address, String telephone, Grocery items){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.items = items;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Grocery getItems() {
        return this.items;
    }

    public void setItems(Grocery items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return firstName + " " 
               + lastName + " "
               + address + " "
               + telephone + " "
                + items.getItem() + " "
                + items.getPrice() + " "
                + items.getStoreName();
    }
}

package coms309.item;


/**
 * Provides the Definition/Structure for the item row
 *
 * @author Vivek Bengre
 */

public class Item {

    private String name;

    private String brandName;

    private double price;

    private double rating;

    public Item(){
        
    }

    public Item(String name, String brandName, double price, double rating){
        this.name = name;
        this.brandName = brandName;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = Math.min(rating, 5);
    }

    @Override
    public String toString() {
        return name + " "
               + brandName + " "
               + price + " "
               + rating;
    }
}

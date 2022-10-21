package ia.storemanager;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Stores a product's data and allows for modifying it.
 * 
 */


public class Product {
    private String name;
    private String barcode;
    private double price;
    private int quantity;
    private String expdate;
    private String dateadded;
    
    /**
     * 
     * Creates a product with name, price, quantity, barcode, expdate, and date it was added.
     * @param price The product's price.
     * @param quantity The product's quantity.
     * @param barcode The product's barcode.
     * @param expdate The product's expiration date.
     * @param dateadded The date when the product was added.
     */
    
    public Product(String name, double price, int quantity, String barcode, String expdate, String dateadded) {
        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.quantity = quantity;
        this.expdate = expdate;
        this.dateadded=dateadded;
    }
    
    
    /**
     * 
     * Creates a product with name, price, quantity, barcode, expdate, but automatically sets the date it was added.
     * @param price The product's price.
     * @param quantity The product's quantity.
     * @param barcode The product's barcode.
     * @param expdate The product's expiration date.
     * Sets the date when the product was added set by setDate() method.
     */

    public Product(String name, double price, int quantity, String barcode, String expdate) {
        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.quantity = quantity;
        this.expdate = expdate;
        this.dateadded=setDate();
    }
    
    
    /**
     * 
     * @return The current date.
     */
    
    public static String setDate(){
        Date d1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        return df.format(d1);
    }
    
    
    /**
     * Sets temporary date manually.
     * @param date A date when the product was added.
     */
    
    public void setTempDate(String date){
        this.dateadded=date;
    }
    
    
    /**
     * 
     * @return The date the product was added.
     */
    
    public String getDateadded(){
        return dateadded;
    }
    
    
    /**
     * 
     * @return The product's barcode.
     */
    
    public String getBarcode() {
        return barcode;
    }
    
    
    /**
     * 
     * @param barcode A product's barcode.
     */
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    
    /**
     * 
     * @return The product's name.
     */
    
    public String getName() {
        return name;
    }
    
    
    /**
     * 
     * @param name A product's name.
     */
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * 
     * @return The product's price.
     */
    
    public double getPrice() {
        return price;
    }
    
    
    /**
     * 
     * @param price A product's price.
     */
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    
    /**
     * 
     * @return The product's quantity.
     */
    
    public int getQuantity() {
        return quantity;
    }
    
    
    /**
     * 
     * @param quantity A product's quantity.
     */
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    /**
     * 
     * @return The product's expiration date.
     */
    
    public String getExpdate() {
        return expdate;
    }
    
    
    /**
     * 
     * @param expdate A product's expiration date.
     */
    
    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }
    
}

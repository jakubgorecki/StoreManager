
package ia.storemanager;

/**
 * Creates a Transaction Item made of a product and its quantity. Allows for modifying it.
 * 
 **/

public class TransactionItem {

    private Product product;
    private int itemq;
    
    /**
     * Creates a transaction item made of a product and a quantity of it.
     * @param product A product.
     * @param itemq A product's quantity.
     */
    
    public TransactionItem(Product product, int itemq) {
        this.product = product;
        this.itemq = itemq;
    }
    
    
    /**
     * 
     * @return The product.
     */
    
    public Product getProduct() {
        return product;
    }

    
    /**
     * 
     * @param product A product.
     */
    
    public void setProduct(Product product) {
        this.product = product;
    }

    
    /**
     * 
     * @return The product's quantity.
     */
    
    public int getItemq() {
        return itemq;
    }

    /**
     * 
     * @param itemq A product's quantity.
     */
    
    public void setItemq(int itemq) {
        this.itemq = itemq;
    }

    
    /**
     * 
     * @return The product's barcode.
     */
    
    public String getBarcode() {
        return product.getBarcode();
    }

    
    /**
     * 
     * @return The product's name.
     */
    
    public String getName() {
        return product.getName();
    }

    
    /**
     * 
     * 
     * @return The product's price.
     */
    
   public double getPrice() {
        return product.getPrice();
    }

    
    /**
     * 
     * @return The product's quantity.
     */
    
    public int getQuantity() {
        return product.getQuantity();
    }

    
    /**
     * 
     * @return The product's expiration date.
     */
    
    public String getExpdate() {
        return product.getExpdate();
    }
    
    
    /**
     * 
     * @return The date when the product was added.
     */
    
    public String getDateadded(){
        return product.getDateadded();
    }
    
    
    /**
     * 
     * @param date A date when the product was added.
     */
    
    public void setDateadded(String date){
        product.setTempDate(date);
    }

}

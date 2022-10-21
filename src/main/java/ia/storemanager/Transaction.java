
package ia.storemanager;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * Stores a transaction data and allows for modifying it.
 * 
 */

public class Transaction {

    private LinkedList<TransactionItem> items;
    private double totalp;
    private double discount;

    /**
     * Creates a transaction made of TransactionItem items list.
     * @param items A TransactionItem Product's list.
     */
    
    public Transaction(LinkedList<TransactionItem> items) {
        this.items = items;
    }

    /**
     * Creates a default transaction with empty items list and no discount.
     */
    
    Transaction() {
        items = new LinkedList<>();
        discount = 0;
    }
    
    
    /**
     * 
     * @param item Adds an item.
     */
    
    public void addItem(TransactionItem item) {
        items.add(item);
    }

    
    /**
     * 
     * @param item Removes an item.
     */
    
    public void removeItem(TransactionItem item) {
        if (item.getItemq() == 0) {
            System.out.println("Can't remove an item since it isn't already added!");
        }
        items.remove(item);
    }

    
    /**
     * 
     * @return The total price without tax.
     */
    
    public double getTotalp() {
        double p = 0;
        for (int i = 0; i < items.size(); i++) {
            double t = (items.get(i).getItemq() * items.get(i).getProduct().getPrice());
            p = p + t;
        }
        return p;
    }
    
    
    /**
     * 
     * @return The total price with tax.
     */
    
    public double getTotalpTAX() {
        return getTotalp() * 0.23;
    }

    
    /**
     * 
     * @return The discount amount.
     */
    
    public double getDiscount() {
        return getTotalp() * discount;
    }

    
    /**
     * Adds or remove a discount of 5%.
     */
    
    public void addRemoveDiscount() {
        if (discount == 0) {
            discount = 0.05;
        } else {
            discount = 0;
        }
    }

    
    /**
     * 
     * @return The price to be paid by customer.
     */
    
    public double getTotalPayable() {
        return getTotalp() + getTotalpTAX() - getDiscount();
    }
    
    /**
     * 
     * @return The list of items.
     */
    
    public LinkedList<TransactionItem> getItems() {
        return items;
    }

    
    /**
     * Adds one more of a given item.
     * @param item A product.
     */
    
    void addMore(TransactionItem item) {
        for (Iterator<TransactionItem> iterator = items.iterator(); iterator.hasNext();) {
            TransactionItem next = iterator.next();
            if (next.equals(item)) {
                next.setItemq(next.getItemq() + 1);
                break;
            }
        }
    }

}

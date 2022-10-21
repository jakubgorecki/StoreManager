package ia.storemanager;

import static ia.storemanager.App.transaction;
import static ia.storemanager.App.Products;
import java.io.IOException;
import java.util.Iterator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Launches the changePOS.fxml window.
 * Calculates the change to be given to the customer and modifies products after a transaction.
 * Allows for changing windows by buttons on the GUI.
 */

public class ChangePOSController {
    @FXML
    private TextField text;
    @FXML
    private Label total;
    @FXML
    private Label change;

    /**
     * Sets the total transaction value on the total Label based on transaction data.
     */
    
    public void initialize() {
        total.setText(String.format("$%.2f", transaction.getTotalPayable()));
    }

    
    /**
     * Launches POS.fxml window.
     * @throws IOException if POS.fxml file is missing.
     */
    
    @FXML
    private void back() throws IOException {
        App.setRoot("POS");
    }

    
    /**
     * Checks if the TransactionItem Products' quantity are not bigger than the product's current stock value.
     * Shows alert and returns if the product's quantity is bigger than the current stock value.
     * Adds products to saleshistory.csv file.
     * Modifies the products' quantity after transaction.
     * Creates new, clear transaction.
     * Writes new, updated products list in products.csv file.
     * Launches mainwindow.fxml window.
     * @throws IOException if the saleshistory.csv file is missing.
     * @throws IOException if the products.csv file is missing.
     * @throws IOException if the mainwindow.fxml file is missing.
     */
    
    @FXML
    private void finish() throws IOException {  
        for (Iterator<TransactionItem> iterator = transaction.getItems().iterator(); iterator.hasNext();) {
            TransactionItem next = iterator.next();
            for (Iterator<Product> iterator1 = Products.iterator(); iterator1.hasNext();) {
                Product next1 = iterator1.next();
                if(next.getProduct().equals(next1)){
                    if(next1.getQuantity()-next.getItemq()<0){
                     Alert alert=new Alert(Alert.AlertType.ERROR);
                     alert.setContentText("You can't substract quantity equal or smaller to zero.");
                     alert.show(); 
                       return;
                    }
                    
                Product p=next.getProduct();
                String product = p.getName() + "," + p.getPrice() + "," + p.getQuantity() + "," + p.getBarcode() + "," + p.getExpdate() + "," + Product.setDate() + "," + next.getItemq();
                App.AddLineInSalesHistory("saleshistory.csv", product);
                TransactionItem temp=new TransactionItem(p,next.getItemq());
                temp.setDateadded(Product.setDate());
                App.SalesProducts.add(0,temp);
                next1.setQuantity(next1.getQuantity()-next.getItemq());
                    break;
                }
            }
        }
        transaction = new Transaction();
        App.WriteFile(Products, "products.csv");
        App.setRoot("mainwindow");
    }
    
    
    /**
     * Changes the change Label value according to the entered TextField text value.
     * Displays the change to be given.
     * @param event A keyboard type.
     */
    
    @FXML
    private void keyTyped(KeyEvent event) {
        try {
            double recievedAmount = Double.parseDouble(text.getText());
            double changeAmount = recievedAmount - transaction.getTotalPayable();
            if (changeAmount > 0) {
                change.setText(String.format("$%.2f", changeAmount));
            } else {
                change.setText("$0.00");
            }
        } catch (Exception e) {
            change.setText("$0.00");
        }
    }

}

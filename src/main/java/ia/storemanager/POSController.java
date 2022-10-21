package ia.storemanager;

import static ia.storemanager.App.Products;
import java.io.IOException;
import javafx.scene.control.TableView;
import java.util.Iterator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;

/**
 * Launches the POS.fxml window. Responsible for Point of Sale system.
 * 
 */
public class POSController {

    Transaction transaction;

    @FXML
    private TableView<TransactionItem> table;
    @FXML
    private Rectangle seven;
    @FXML
    private Rectangle eight;
    @FXML
    private Rectangle nine;
    @FXML
    private Rectangle four;
    @FXML
    private Rectangle five;
    @FXML
    private Rectangle six;
    @FXML
    private Rectangle one;
    @FXML
    private Rectangle two;
    @FXML
    private Rectangle three;
    @FXML
    private Rectangle zero;
    @FXML
    private Rectangle enter;
    @FXML
    private Rectangle dot;
    @FXML
    private Rectangle backspace;
    @FXML
    private TextField textfield;
    @FXML
    private TableColumn<Product, Double> price;
    @FXML
    private TableColumn<TransactionItem, Integer> quantity;
    @FXML
    private TableColumn<Product, String> barcode;
    @FXML
    private TableColumn<Product, String> expdate;
    @FXML
    private Label lbltotal;
    @FXML
    private Label lblsubtotal;
    @FXML
    private Label lbltax;
    @FXML
    private Label lbldiscounts;
    @FXML
    private TableColumn<TransactionItem, Product> product;
    @FXML
    private TableColumn<Product, String> name;

    /**
     * Initializes transaction, loads previous unfinished transaction.
     * Adds TransactionItems from previous unfinished transaction.
     * Initializes setupTable() method for designing the table.
     */
    
    public void initialize() {
        transaction = App.transaction;
        table.getItems().clear();
        for (Iterator<TransactionItem> iterator = transaction.getItems().iterator(); iterator.hasNext();) {
            TransactionItem next = iterator.next();
            table.getItems().add(next);
        }
        setupTable();
 
        calculatePrice();
        textfield.requestFocus();
    }
    
    
    /**
     * Switches to mainwindow.fxml window.
     * @throws IOException when mainwindow.fxml file is missing.
     */
    
    @FXML
    private void switchToMainWindow() throws IOException {
        App.setRoot("mainwindow");
    }

    
    /**
     * Enters clicked number on the numpad into the textfield. 
     * @param event a numpad's mouse click.
     */
    
    @FXML
    private void lblClicked(MouseEvent event) {
        Label lbl = (Label) event.getSource();
        textfield.setText(textfield.getText() + lbl.getText());
        textfield.requestFocus();
    }
    
    
    /**
     * After clicking enter button, checks if the textfield matches any barcode.
     */
    
    @FXML
    private void lblEnter() {
        checkCode();
    }
    
    
    /**
     * Removes one character from the textfield after clicking backspace button.
     */
    
    @FXML
    private void lblBackspace() {
        String s = textfield.getText();
        if (!s.isEmpty()) {
            textfield.setText(s.substring(0, s.length() - 1));
        }
        textfield.requestFocus();
    }
    
    /**
     * Checks if the entered barcode matches any product in Products list
     * @param barCode an entered barcode in the TextField.
     * @return Product next if barcode matches a product.
     * @return null if the barcode doesn't match any product.
     */
    
    private Product getProduct(String barCode) {
        for (Iterator<Product> iterator = Products.iterator(); iterator.hasNext();) {
            Product next = iterator.next();
            if (next.getBarcode().equals(barCode)) {
                return next;
            }
        }
        return null;
    }
    
    
    
    /**
     * Designs the TableView table and its columns.
     */
    
    public void setupTable() {
        product.setReorderable(false);
        price.setReorderable(false);
        quantity.setReorderable(false);
        barcode.setReorderable(false);
        name.setSortable(false);
        expdate.setSortable(false);
        product.setStyle("-fx-alignment: CENTER;");
        name.setStyle("-fx-alignment: CENTER;");
        price.setStyle("-fx-alignment: CENTER;");
        quantity.setStyle("-fx-alignment: CENTER;");
        barcode.setStyle("-fx-alignment: CENTER;");
        expdate.setStyle("-fx-alignment: CENTER;");

        product.setCellValueFactory(new PropertyValueFactory<>("product"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("itemq"));
        barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        expdate.setCellValueFactory(new PropertyValueFactory<>("expdate"));
    }
    
    
    /**
     * Sets an appropriate value for lblsubtotal, lbltax, lbldiscounts, lbltotal labels.
     */
    
    private void calculatePrice() {

        lblsubtotal.setText(String.format("$%.2f", transaction.getTotalp()));
        lbltax.setText(String.format("$%.2f", transaction.getTotalpTAX()));
        lbldiscounts.setText(String.format("$%.2f", transaction.getDiscount()));
        lbltotal.setText(String.format("$%.2f", transaction.getTotalPayable()));
        textfield.requestFocus();
    }
    
    
    /**
     * Adds and removes a discount.
     * Calculates the price after adding or removing a discount.
     */
    
    @FXML
    private void addRemoveDiscount() {
        transaction.addRemoveDiscount();
        calculatePrice();
    }

    
    /**
     * Adds a bag with a default barcode.
     */
    
    @FXML
    private void addBag() {
        textfield.setText("0000000000000");
        checkCode();
    }

    
    /**
     * Adds one more of a quantity of the selected item.
     */
    
    @FXML
    private void addMore() {
        TransactionItem item = table.getSelectionModel().getSelectedItem();
        if (item != null) {
            transaction.addMore(item);
            item.setItemq(item.getItemq());
        }
            table.refresh();
            calculatePrice();
    }
    
    
    /**
     * Removes one quantity of the selected item from the table.
     * Shows an alert if the quantity is going to be negative.
     */
    
    @FXML
    private void remove() {
        TransactionItem item = table.getSelectionModel().getSelectedItem();
        if (item!=null){
            if(item.getItemq()<=0){
               Alert alert=new Alert(Alert.AlertType.ERROR);
               alert.setContentText("You can't substract quantity equal or smaller to zero.");
               alert.show(); 
            }
            else if(item.getItemq()>0)
            item.setItemq(item.getItemq()-1);
        }
        table.refresh();
        calculatePrice();
    }
    
    
    /**
     * Removes a TransactionItem entry from the TableView table.
     */
    
    @FXML
    private void removeEntry(){
        TransactionItem item = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(item);
        transaction.removeItem(item);
        table.refresh();
        calculatePrice();
    }
    
    
    /**
     * Switches to changePOS.fxml window.
     * @throws IOException when changePOS.fxml file is missing.
     */
    
    @FXML
    private void finish() throws IOException {
        App.setRoot("changePOS");
    }
    
    
    /**
     * Checks if the current entered barcode matches any product.
     * @param event a keyboard or a barcode scanner type. 
     */
    
    @FXML
    private void barcodeTyped(KeyEvent event) {
        checkCode();
    }
    
    
    /**
     * Checks if the entered barcode matches any product, and adds it as a TransactionItem to the table.
     * If the product is already added, it increases its quantity by 1.
     */
    
    private void checkCode() {
        boolean c=false;
        String barCode = textfield.getText();
        Product product = getProduct(barCode);
        if (product != null) {
             for (Iterator<TransactionItem> iterator = transaction.getItems().iterator(); iterator.hasNext();) {
            TransactionItem next = iterator.next();
                if(product.getBarcode().equals(next.getBarcode())){
                    next.setItemq(next.getItemq()+1);
                   table.refresh();
                    c=true;
             }
             }
             if(c==false){
            TransactionItem item = new TransactionItem(product, 1);
            table.getItems().add(item);
            transaction.addItem(item);
             }
            textfield.setText("");
            calculatePrice();
        }
        textfield.requestFocus();
    }
            
        
        
        
    
}

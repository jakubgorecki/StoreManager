package ia.storemanager;
import static ia.storemanager.App.isDemand;
import static ia.storemanager.App.Products;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.layout.StackPane;

    /**
     * Launches mainwindow.fxml window, creates and modifies the notification table.
     * Allows for changing windows by buttons on the GUI.
     * 
     */

public class MainWindowController {
    @FXML
    private TableView table;
    @FXML
    private TableColumn issue;
    @FXML
    private TableColumn name;
    
    
    /**
     * Launches populatetable() method at the beginning.
     */
    
    public void initialize(){
        populatetable();
    }
    
    
    /**
     * Creates and designs notification table.
     * Checks for products with quantity lower than 10 and incoming expiration date.
     * Adds products to the notification table which match the above mentioned criteria.
     */
    
    public void populatetable() {
        StackPane placeHolder = new StackPane();
        placeHolder.setStyle("-fx-background-color: transparent;");
        table.setPlaceholder(placeHolder);
        issue.setReorderable(false);
        name.setReorderable(false);
        issue.setStyle( "-fx-alignment: CENTER;");
        name.setStyle( "-fx-alignment: CENTER;");
        ObservableList<MainWindowNotification> list = FXCollections.observableArrayList();
        issue.setCellValueFactory(new PropertyValueFactory<>("issue"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (int i = 0; i < Products.size(); i++) {
            if(Products.get(i).getQuantity()<=10){
                MainWindowNotification product= new MainWindowNotification("Low quantity ("+Products.get(i).getQuantity()+")",Products.get(i).getName());
                list.add(product);
            }
            if(!"00/00/0000".equals(Products.get(i).getExpdate())){
                String sDate=Products.get(i).getExpdate();
                LocalDate date=LocalDate.parse(sDate, formatter);
                
            if(expdate(date)){
               MainWindowNotification product= new MainWindowNotification("Incoming exp. date ("+Products.get(i).getExpdate()+")",Products.get(i).getName());
               list.add(product);
            }
        }
        }
        table.setItems(list);
    }
    
    
    /**
     * Switches to products.fxml window.
     * @throws IOException when products.fxml is missing.
     */
    
    @FXML
    private void switchToProducts() throws IOException {
        App.setRoot("products");
        
    }
    
    
    /**
     * Switches to POS.fxml window.
     * @throws IOException when POS.fxml is missing.
     */
    
    @FXML
    private void switchToTransaction() throws IOException {
        App.setRoot("POS"); 
    }
    
    
    /**
     * Switches to aprice1.fxml window.
     * @throws IOException when aprice1.fxml is missing.
     */
    
    @FXML
    private void switchToAprice1() throws IOException {
        isDemand=true;
        App.setRoot("aprice1");
        
    }
    
    
    /**
     * Switches to salesHistory.fxml window.
     * @throws IOException when salesHistory.fxml is missing.
     */
    
        @FXML
    private void switchToSalesHistory() throws IOException {
        App.setRoot("salesHistory");
    }
    
    
    /**
     * Checks if the product's expiration date is in next 7 days.
     * @param date1 A product's expiration date.
     * @return True if expiration date is in next 7 days, false if it's not.
     */
    
     public static boolean expdate(LocalDate date1) {
         LocalDate date2 = LocalDate.now();
             if(!date1.isAfter(date2.plusDays(7)))
                 return true;
             else
                 return false;
}
}

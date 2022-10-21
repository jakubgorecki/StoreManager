package ia.storemanager;
import static ia.storemanager.App.Products;
import static ia.storemanager.App.popup;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Launches products.fxml window, displays the table of products and allows for modifying them.
 * 
 */

public class ProductsController {
    @FXML
    private TableView table;
    @FXML
    private TableColumn id;
    @FXML
    private TableColumn price;
    @FXML
    private TableColumn quantity;
    @FXML
    private TableColumn barcode;
    @FXML
    private TableColumn expdate;
    
    /**
     * Launches initTable() and populateTable() methods at the beginning.
     */
    
    public void initialize(){
        initTable();
        populateTable();
    }
    
    
    /**
     * Switches to mainwindow.fxml window.
     * @throws IOException when mainwindow.fxml is missing.
     */
    
    @FXML
    private void switchToMainWindow() throws IOException {
        App.setRoot("mainwindow");
    }
   
    
    /**
     * Designs the table with products.
     */
    
    public void initTable(){
        id.setReorderable(false);
        price.setReorderable(false);
        quantity.setReorderable(false);
        barcode.setReorderable(false);
        barcode.setSortable(false);
        expdate.setSortable(false);
        id.setStyle( "-fx-alignment: CENTER;");
        price.setStyle( "-fx-alignment: CENTER;");
        quantity.setStyle( "-fx-alignment: CENTER;");
        barcode.setStyle( "-fx-alignment: CENTER;");
        expdate.setStyle( "-fx-alignment: CENTER;");
    }
    
    
    /**
     * Populates the table with products and their data.
     */
    
    public void populateTable(){
        ObservableList<Product> list=FXCollections.observableArrayList(Products); 
        id.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        barcode.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        expdate.setCellValueFactory(new PropertyValueFactory<Product, String>("expdate"));
        table.setItems(list);
    }
    
    
    /**
     * Launches a popup window of addProduct.fxml file.
     * @throws IOException when addProduct.fxml is missing.
     */
    
    public void addProduct() throws IOException{
        popupWindow("addProduct.fxml");
    }
    
    
    /**
     * Passes the given product information to EditProductController class.
     * Launches a popup window of editProduct.fxml file.
     * @throws IOException when editProduct.fxml is missing.
     */
    
    public void editProduct() throws IOException{
        checkSelect();
            if(checkSelect()){
            Product p=(Product) table.getSelectionModel().getSelectedItem();
            App.setPassProduct(p);
            popupWindow("editProduct.fxml");    
            }
    }
    
    
    /**
     * Launches a popup window of s file.
     * @param s A .fxml window file name.
     * @throws IOException when s .fxml file name doesn't exist.
     */
    
    public void popupWindow(String s) throws IOException{
        popup=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Scene scene=new Scene(loader.load(), 487,335);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setScene(scene);
        popup.show();
        if(s.equals("addProduct.fxml"))
        popup.setTitle("Add new product");
        else
        popup.setTitle("Edit product");
    }
    
    
    /**
     * Shows a confirmation alert before removing the selected product.
     * Removes the selected product from Products list, then from products.csv file.
     * @throws IOException when products.csv file is missing.
     */
    
    public void removeProduct() throws IOException{
        checkSelect();
        if(checkSelect()){
        Product p=(Product) table.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove a product");
        alert.setContentText("Are you sure you want to remove "+p.getName()+"?");
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                System.out.println("removing");
                Products.remove(p);
            try {
                App.WriteFile(Products,"products.csv");
            }  catch (IOException ex) {
                ex.printStackTrace();
            }
            }}); 
        table.getItems().clear();
        populateTable();
        table.refresh();        
        }
    }
    
    
    /**
     * Shows a confirmation alert before removing all products.
     * Removes all products in products.csv file.
     * @throws IOException when products.csv file is missing.
     */
    
    @FXML
    private void removeAll() throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove all products");
        alert.setContentText("Are you sure you want to remove all products? It cannot be reversed.");
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                System.out.println("removing");
                Products.clear();
            try {
                App.WriteFile(Products,"products.csv");
            }  catch (IOException ex) {
                ex.printStackTrace();
            }
            }}); 
        table.getItems().clear();
        populateTable();
        table.refresh();
    }
    
    
    /**
     * Checks if a product in the table has been selected.
     * @return true if selected, shows error alert if no product is selected.
     */
    
    public boolean checkSelect(){
            if(table.getSelectionModel().getSelectedItem() == null){
               Alert alert=new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Something is wrong. Please check if you have clicked on the product cell.");
               alert.show(); 
               return false;
            }
            else {
                return true;
            }
    }
    }
            


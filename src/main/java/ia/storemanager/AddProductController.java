package ia.storemanager;

import static ia.storemanager.App.Products;
import static ia.storemanager.App.popup;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * Launches addProduct.fxml window and allows for adding a product.
 */

public class AddProductController {

    @FXML
    private TextField priceid;
    @FXML
    private TextField quantityid;
    @FXML
    private TextField textid;
    @FXML
    private TextField barcodeid;
    @FXML
    private TextField expdateid;

    /**
     * Adds priceid and quantityid TextField listeners, preventing the user from entering symbols and letters
     * into a number TextField.
     */
    
    public void initialize() {
        priceid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    priceid.setText(oldValue);
                }
            }
        });
        quantityid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    quantityid.setText(oldValue);
                }
            }
        });
    }
    
    
    /**
     * Closes the popup addProduct.fxml window.
     * @throws IOException when popup Stage is missing.
     */
    
    @FXML
    private void switchToProducts() throws IOException {
        popup.close();
    }

    
    /**
     * Assigns new entered values to a new product instance.
     * Shows an error alert if expiration date is entered incorrectly.
     * Shows an error alert if any information is missing.
     * Updates the products.csv file.
     * Closes the popup editProduct.fxml window.
     * @throws IOException when popup Stage is missing.
     * @throws IOException when products.csv file is missing.
     */
    
    @FXML
    private void switchAndSave() throws IOException {
        if (!priceid.getText().isEmpty() && !quantityid.getText().isEmpty() && !textid.getText().isEmpty() && !barcodeid.getText().isEmpty() && expcheck(expdateid)) {
            String product = textid.getText() + "," + priceid.getText() + "," + quantityid.getText() + "," + barcodeid.getText() + "," + expdateid.getText() + "," + Product.setDate();
            Product product1 = new Product(textid.getText(), Double.parseDouble(priceid.getText()), Integer.parseInt(quantityid.getText()), barcodeid.getText(), expdateid.getText(), Product.setDate());
            App.AddLine(Products, "products.csv", product);
            Products.add(product1);
            App.resetScene();
            popup.close();
            

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something is wrong. Please check if you have entered everything correctly.");
            alert.show();
        }
    }
    
    
    /**
     * Checks if the expiration date is entered correctly.
     * @param x a TextField with an expiration date.
     * @return true if entered correctly, false if it's not.
     * @return true if its empty, and set a default expiration date of 00/00/0000.
     */
    
    public boolean expcheck(TextField x) {
        if(x.getText().isEmpty()){
            x.setText("00/00/0000");
            return true;
        }
        try{
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date=LocalDate.parse(x.getText(), formatter);
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

}

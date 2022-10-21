
package ia.storemanager;
import static ia.storemanager.App.Products;
import static ia.storemanager.App.popup;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * Launches the editProduct.fxml window.
 * Allows for editing a given product.
 */

public class EditProductController {
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
    Product p;  
    
    /**
     * Sets the product to be edited from the passing parameter.
     * Initializes TextFields values by setPrompts() method.
     */
    public void initialize() {
        p=App.getPassProduct();
        setPrompts();
    }
    
    
    /**
     * Sets TextFields text prompt data based on current product's values.
     */
    
    @FXML
    public void setPrompts(){
       textid.setText(p.getName());
       priceid.setText(String.valueOf(p.getPrice()));
       quantityid.setText(String.valueOf(p.getQuantity()));
       barcodeid.setText(String.valueOf(p.getBarcode()));
       expdateid.setText(p.getExpdate());
    }
    
    /**
     * Closes the popup editProduct.fxml window.
     * @throws IOException when popup Stage is missing.
     */
    
    @FXML   
    private void switchToProducts() throws IOException { 
        popup.close();
    }
    
    
    /**
     * Assigns new entered values to the product's variables.
     * Shows an error alert if expiration date is entered incorrectly.
     * Updates the products.csv file.
     * Closes the popup editProduct.fxml window.
     * @throws IOException when popup Stage is missing.
     * @throws IOException when products.csv file is missing.
     */
    
    @FXML
    private void switchAndSave() throws IOException {
            if(!priceid.getText().isEmpty())
                p.setPrice(Double.parseDouble(priceid.getText()));
            if(!quantityid.getText().isEmpty())
                p.setQuantity(Integer.parseInt(quantityid.getText()));
            if(!textid.getText().isEmpty())
                p.setName(textid.getText());
            if(!barcodeid.getText().isEmpty())
                p.setBarcode(barcodeid.getText());
            if(!expdateid.getText().isEmpty()&& expcheck(expdateid))
                p.setExpdate(expdateid.getText());
            else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something is wrong. Please check if you have entered the Expiration date correctly.");
            alert.show(); 
            }
            if(expcheck(expdateid)){
            App.WriteFile(Products, "products.csv");
            App.resetScene();
            popup.close();
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


package ia.storemanager;

import static ia.storemanager.App.datapoints;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Test
 * Launches aprice2.fxml window.
 * Allows for entering both demand and supply curves datapoints data.
 * Allows for changing windows by buttons on the GUI.
 */

public class Aprice2Controller {
    @FXML
    private TextField price;
    @FXML
    private TextField quantity;
    @FXML
    private ImageView next;
    @FXML
    private ImageView finish;
    public static double[][] data;
    public int c;
    
    /**
     * Initializes a 2D Array with two rows.
     * Saves c as a counter variable with datapoints value.
     */
    
    public void initialize(){
        data=new double[datapoints][2];
        c=datapoints;
    }
    
    
    /**
     * Switches to aprice1.fxml window.
     * @throws IOException when aprice1.fxml file is missing.
     */
    
    @FXML
    private void switchToAprice1() throws IOException {
        App.setRoot("aprice1");
    }
    
    
    /**
     * Adds entered price and quantity textfields' values into data 2D Array.
     * Changes the counter. When counter reaches 0, shows a finish button on the GUI.
     * 
     */
    
    @FXML
    private void next() { 
        if(c!=0){
            data[c-1][0]=Double.valueOf(price.getText());
            data[c-1][1]=Double.valueOf(quantity.getText());
            c--;
            price.setText("");
            quantity.setText("");
            if(c==0){
                next.setVisible(false);
                finish.setVisible(true);
            }
        }
    }
    
    
    /**
     * Sorts data using sortData(double[][] a) method, switches to aprice3.fxml window.
     * @throws IOException when aprice3.fxml file is missing.
     */
    
    @FXML
    private void switchToAprice3() throws IOException {
        sortData(data);
        App.setRoot("aprice3");
    }
    
    
    /**
     * Sorts param a 2D Array using bubble sort algorithm,
     * @param a A 2D Array.
     */
    
    public void sortData(double[][] a){
        for (int row = 0; row < a.length-1; row++) {
                if(a[row][1]>a[row+1][1]){
                    double temp=a[row][1];
                    double temp2=a[row][0];
                    a[row][1]=a[row+1][1];
                    a[row][0]=a[row+1][0];
                    a[row+1][1]=temp;
                    a[row+1][0]=temp2;
                }
        }
    }

    }




package ia.storemanager;
import static ia.storemanager.App.datapoints;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Launches aprice.fxml window and allows for entering the datapoints.
 * Allows for changing windows by buttons on the GUI.
 */

public class Aprice1Controller {
    @FXML
    private TextField textfield;
    
    
    /**
     * Switches to mainwindow.fxml window.
     * @throws IOException when mainwindow.fxml file is missing.
     */
    
    @FXML
    private void switchToMainWindow() throws IOException {
        App.setRoot("mainwindow");
    
    }
    
    
    /**
     * Reads the entered datapoints value in the textfield.
     * Saves it to datapoints variable.
     * Switches to aprice2.fxml window.
     * @throws IOException when aprice2.fxml file is missing.
     */
    
    @FXML
    private void switchToAprice2() throws IOException {        
    datapoints=Integer.valueOf(textfield.getText());
        App.setRoot("aprice2");
    }
}

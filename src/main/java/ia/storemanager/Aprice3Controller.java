
package ia.storemanager;

import java.io.IOException;
import javafx.fxml.FXML;
import static ia.storemanager.App.isDemand;
import static ia.storemanager.Aprice2Controller.data;
import static ia.storemanager.App.first;
import java.util.Arrays;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * Launches aprice3.fxml window.
 * Displays a LineChart diagram based on data 2D Array.
 * Allows for changing windows by buttons on the GUI.
 */

public class Aprice3Controller {
    @FXML LineChart<Number,Number> graph;
    
    /**
     * Initializes the LineChart graph.
     * Checks if data array is a supply or demand curve data set.
     * Adds data points from data array onto the graph.
     */
    
    public void initialize() {
        graph.setTitle("Appropriate price diagram");
        graph.setAnimated(false);
        XYChart.Series second=new XYChart.Series();
        first.setName("Demand Curve");
        second.setName("Supply Curve");
        if(isDemand){
        for (int i = 0; i < data.length; i++) {
          first.getData().add(new XYChart.Data(String.valueOf(data[i][1]),data[i][0]));
        }
        }
        else {
         for (int i = 0; i < data.length; i++) {
          second.getData().add(new XYChart.Data(String.valueOf(data[i][1]),data[i][0]));
        }
        graph.getData().add(second);
        }
        
        graph.getData().add(first);
    }
    
    
    /**
     * Defaults isDemand variable to true.
     * Clears the data array.
     * Switches to mainwindow.fxml window.
     * @throws IOException when mainwindow.fxml file is missing.
     */
        @FXML
    private void switchToMainWindow() throws IOException {
        isDemand=true;
        Arrays.fill(data, null);
        App.setRoot("mainwindow");
    }
    
    
    /**
     * Sets isDemand variable to false.
     * Swtiches to aprice1.fxml window.
     * @throws IOException when aprice1.fxml file is missing.
     */
    
        @FXML
    private void addSupply() throws IOException {
        isDemand=false;
        App.setRoot("aprice1");
    }
}
           
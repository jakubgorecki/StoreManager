package ia.storemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.chart.XYChart;

/**
 * Loads file data into Products and SalesProducts LinkedLists
 * Launches GUI
 * Holds passing parameters
 * Holds methods for reading and writing .csv files
 * Holds methods to switch .fxml windows
 */

public class App extends Application {

    public static LinkedList<Product> Products = new LinkedList<>();
    public static LinkedList<TransactionItem> SalesProducts = new LinkedList<>();
    public static Transaction transaction = new Transaction();
    public static Product passProduct;
    public static int datapoints;
    public static Stage popup;
    private static Scene scene;
    private static String lastScene;
    private static Stage stage;
    public static boolean isDemand=true;
    public static XYChart.Series first=new XYChart.Series();
    
    /**
     * Loads mainwindow.fxml file, creates an initial stage.
     * @param stage the main stage.
     * @throws IOException when mainwindow.fxml file is missing.
     */
    
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        scene = new Scene(loadFXML("mainwindow"));
        stage.setScene(scene);
        stage.show();
        stage.sizeToScene();
    }

    /**
     * Allows for switching the .fxml windows.
     * @param fxml An fxml file name
     * @throws IOException when no FXML is to be loaded.
     */
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        stage.sizeToScene();
        lastScene = fxml;
    }

    
    /**
     * Resets the scene after a popup window.
     * @throws IOException when lastScene is missing.
     */
    
    public static void resetScene() throws IOException {
        setRoot(lastScene);
    }
    
    
    /**
     * Loads the FXML file.
     * @param fxml an fxml file name
     * @return the fxml loader.
     * @throws IOException when a file name doesn't exist.
     */
    
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    
    /**
     * Reads products.csv and saleshistotry.csv files into Products and SalesProducts lists.
     * Launches the mainwindow.fxml window.
     * @throws IOException when the .csv files are missing.
     */
    
    public static void main(String[] args) throws IOException {
        ReadFile(Products, "products.csv");
        ReadSalesHistory(SalesProducts, "saleshistory.csv");
        launch();
    }
    
    
    /**
     * A Product passing parameter getter. 
     * @return Product
     */
    
    public static Product getPassProduct() {
        return passProduct;
    }
    
    
    /**
     * A Product passing parameter setter.
     * @param passProduct a Product to be passed.
     */
    
    public static void setPassProduct(Product passProduct) {
        App.passProduct = passProduct;
    }
    
    
    /**
     * Reads the products.csv file
     * @param Products a Products list to which the file data should be loaded.
     * @param file a string with the file name.
     * @throws FileNotFoundException if the file doesn't exist.
     * @throws IOException if data is missing.
     */
    
    public static void ReadFile(LinkedList<Product> Products, String file) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        String line = "";
        String splitBy = ",";

        while ((line = bfr.readLine()) != null) {
            String[] product = line.split(splitBy);
            Product x = new Product(product[0], Double.parseDouble(product[1]), Integer.parseInt(product[2]), product[3], product[4], product[5]);
            Products.add(x);

        }

    }

    
    /**
     * Adds a product's data in Products list.
     * @param Products a Products list.
     * @param file_name a string with the file name.
     * @param product a string with the product's data.
     * @throws FileNotFoundException if the file doesn't exist.
     * @throws IOException if data is missing.
     */
    
    public static void AddLine(LinkedList<Product> Products, String file_name, String product) throws FileNotFoundException, IOException {
        File file = new File(file_name);
        FileWriter writer = new FileWriter(file, true);
        writer.write("\n");
        writer.write(product);
        writer.flush();
        writer.close();
    }
    
    
    /**
     * Writes a new file with Products data.
     * @param Products a Products list.
     * @param file_name a new file name.
     * @throws FileNotFoundException if the file can't be created.
     * @throws IOException if data is missing.
     */
    
    public static void WriteFile(LinkedList<Product> Products, String file_name) throws FileNotFoundException, IOException {
        File file = new File(file_name);
        FileWriter writer = new FileWriter(file, false);
        for (Product product : Products) {
            writer.write(product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getBarcode() + "," + product.getExpdate() + "," + product.getDateadded());
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    
    /**
     * Reads a file and reverses the order into a TransactionItem list.
     * @param list a TransactionItem LinkedList.
     * @param file a file name.
     * @throws FileNotFoundException if the file doesn't exist.
     * @throws IOException if data is entered incorrectly.
     */
    
    public static void ReadSalesHistory(LinkedList<TransactionItem> list, String file) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        String line = "";
        String splitBy = ",";
        ArrayList<String[]> data = new ArrayList<>();
        while ((line = bfr.readLine()) != null) {
            try {
                String[] product = line.split(splitBy);
                data.add(product);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        for (int i = data.size() - 1; i >= 0; i--) {
            String[] product = data.get(i);
            try {
            TransactionItem x = new TransactionItem(new Product(product[0], Double.parseDouble(product[1]), Integer.parseInt(product[2]), product[3], product[4], product[5]), Integer.parseInt(product[6]));
                list.add(x);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    
    /**
     * Adds a Product data in a file_name file.
     * @param file_name a file name.
     * @param product a product to be added.
     * @throws FileNotFoundException if the file doesn't exist.
     * @throws IOException if product's data is incorrect.
     */
    
    public static void AddLineInSalesHistory(String file_name, String product) throws FileNotFoundException, IOException {
        File file = new File(file_name);
        FileWriter writer = new FileWriter(file, true);
        writer.write("\n");
        writer.write(product);
        writer.flush();
        writer.close();
    }
    

}

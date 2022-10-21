
package ia.storemanager;

import static ia.storemanager.App.SalesProducts;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import com.pdfjet.*;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Launches the salesHistory.fxml window and its GUI.
 * Shows revenue from the previous week and month.
 * Generates a PDF file with a weekly or monthly revenue report.
 * IMPORTANT: With the current edition of the open-source PDFJet dependency, TextColumn is not included, therefore it is temporarily disabled.
 */

public class SalesHistoryController {

    @FXML
    private TableView<TransactionItem> table;
    @FXML
    private TableColumn<Product, Double> price;
    @FXML
    private TableColumn<TransactionItem, Integer> quantity;
    @FXML
    private TableColumn<Product, String> barcode;
    @FXML
    private TableColumn<TransactionItem, Product> product;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, String> date;
    @FXML
    private Label week;
    @FXML
    private Label month;
    private LinkedList<TransactionItem> weeklytable;
    private LinkedList<TransactionItem> monthlytable;
    private double totalweek;
    private double totalmonth;
    
    /**
     * Initializes the table and statistics by setupTable() and stats() methods. 
     */
    
    public void initialize() {
        setupTable();
        stats();
    }
    
    
    /**
     * Loads data into weeklytable LinkedList for a weekly PDF report file.
     * @throws IOException when saleshistory.csv file is missing.
     */
    
    public void initArrayWeekly() throws IOException{
        App.ReadSalesHistory(weeklytable, "saleshistory.csv");
        LocalDate date=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (int i = 0; i < weeklytable.size(); i++) {
            String sDate=weeklytable.get(i).getDateadded();
            System.out.println("sdate "+sDate);
            LocalDate date2=LocalDate.parse(sDate,formatter);
            System.out.println(date2);
            TransactionItem temp=weeklytable.get(i);
              if(date2.isBefore(date.minusDays(8))){
                  weeklytable.remove(weeklytable.get(i));  
                  i--;
              }
        }
        for (int i = 0; i < weeklytable.size(); i++) { 
            for (int j = i+1; j < weeklytable.size(); j++) {
                if(weeklytable.get(i).getName().equals(weeklytable.get(j).getName())){
                    weeklytable.get(i).setItemq(weeklytable.get(i).getItemq()+weeklytable.get(j).getItemq());
                    weeklytable.remove(j);
                    j--;
                }
            }
        }
        for (int i = 0; i < weeklytable.size(); i++) {
        }
    }
    
    
    /**
     * Loads data into monthlytable LinkedList for a monthly PDF report file.
     * @throws IOException when saleshistory.csv file is missing.
     */
    
    public void initArrayMonthly() throws IOException{
        App.ReadSalesHistory(monthlytable, "saleshistory.csv");
        LocalDate date=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (int i = 0; i < monthlytable.size(); i++) {
            String sDate=monthlytable.get(i).getDateadded();
            LocalDate date2=LocalDate.parse(sDate,formatter);
            TransactionItem temp=monthlytable.get(i);
              if(date2.isBefore(date.minusDays(31))){
                  monthlytable.remove(monthlytable.get(i));  
                  i--;
              }
        }
        for (int i = 0; i < monthlytable.size(); i++) { 
            for (int j = i+1; j < monthlytable.size(); j++) {
                if(monthlytable.get(i).getName().equals(monthlytable.get(j).getName())){
                    monthlytable.get(i).setItemq(monthlytable.get(i).getItemq()+monthlytable.get(j).getItemq());
                    monthlytable.remove(j);
                    j--;
                }
            }
        }
    }
    
    
    /**
     * Calculates revenue from the last week and month to set it in the week and month labels.
     */
    
    public void stats(){
        LocalDate date=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < SalesProducts.size(); i++) {
            String sDate=SalesProducts.get(i).getDateadded();
            LocalDate date2=LocalDate.parse(sDate,formatter);
            if(date2.isAfter(date.minusDays(8)))
                totalweek=totalweek+SalesProducts.get(i).getPrice()*SalesProducts.get(i).getItemq();
           
            if(date2.isAfter(date.minusDays(31)))
                   totalmonth=totalmonth+SalesProducts.get(i).getPrice()*SalesProducts.get(i).getItemq();
        }
        week.setText(String.format("$%.2f",totalweek));
        month.setText(String.format("$%.2f",totalmonth));
    }
    
    /**
     * Generates a weekly revenue report.
     * @throws Exception when any files are missing.
     */
    
    @FXML
    private void weekly() throws Exception{
        initArrayWeekly();
        pdf("week");
    }
    
    
    /**
     * Generates a monthly revenue report. 
     * @throws Exception when any files are missing.
     */
    
    @FXML
    private void monthly() throws Exception{
        initArrayMonthly();
        pdf("month");
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
     * Creates and fills the table made of SalesProducts. 
     * Designs the table.
     */
    
    public void setupTable() {
        LinkedList<TransactionItem> tableview=new LinkedList<TransactionItem>(SalesProducts);
        ObservableList<TransactionItem> list=FXCollections.observableArrayList(tableview); 
        product.setReorderable(false);
        price.setReorderable(false);
        quantity.setReorderable(false);
        barcode.setReorderable(false);
        name.setSortable(false);
        date.setSortable(false);
        product.setStyle("-fx-alignment: CENTER;");
        name.setStyle("-fx-alignment: CENTER;");
        price.setStyle("-fx-alignment: CENTER;");
        quantity.setStyle("-fx-alignment: CENTER;");
        barcode.setStyle("-fx-alignment: CENTER;");
        date.setStyle("-fx-alignment: CENTER;");
        week.setAlignment(Pos.CENTER);
        month.setAlignment(Pos.CENTER);
        product.setCellValueFactory(new PropertyValueFactory<TransactionItem, Product>("product"));
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<TransactionItem, Integer>("itemq"));
        barcode.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        date.setCellValueFactory(new PropertyValueFactory<Product, String>("dateadded"));
        table.setItems(list);
    }
    
    /**
     * Creates a PDF file
     * Generates text with Total revenue, and generates a table with weekly or monthly sales list.
     * Saves it to StoreManager folder.
     * @param s the time period.
     * @throws Exception if the PDF file wasn't created.
     */
    
    public void pdf(String s) throws Exception{
        File bill=new File("Sales Report.pdf");
        FileOutputStream billout=new FileOutputStream(bill);
        PDF pdf=new PDF(billout);
        Page page = new Page(pdf, A4.PORTRAIT);
        Font f1=new Font(pdf, CoreFont.HELVETICA_BOLD);
        Font f2=new Font(pdf, CoreFont.HELVETICA);

        Table table=new Table();
        List<List<Cell>> tableData=new ArrayList<List<Cell>>();
        List<Cell> tableRow=new ArrayList<Cell>();
        //TextColumn column = new TextColumn();

        //column.setWidth(200);
        TextLine text=new TextLine(f1, "Store Manager's Sales Report");        
        Paragraph p1=new Paragraph();
        p1.setAlignment(Align.JUSTIFY);
        p1.add(text);
        //column.addParagraph(p1);
        //column.setLocation(210f, 50f);
        //column.drawOn(page);
        
        //TextColumn total = new TextColumn();
        //total.setWidth(200);
        TextLine totalText=new TextLine(f1);
        if(s.equals("week"))
            totalText.setText("Total revenue: "+String.valueOf(totalweek));
        else if(s.equals("month"))
            totalText.setText("Total revenue: "+String.valueOf(totalmonth));
        
        Paragraph p2=new Paragraph();
        p2.setAlignment(Align.JUSTIFY);
        p2.add(totalText);
        //total.addParagraph(p2);
        //total.setLocation(235f, 70f);
        //total.drawOn(page);
        
        Cell cell=new Cell(f1,"Product");
        tableRow.add(cell);
        cell=new Cell(f1,"Quantity Sold");
        tableRow.add(cell);
        cell=new Cell(f1,"Price");
        tableRow.add(cell);
        cell=new Cell(f1,"Product Revenue");
        tableRow.add(cell);
        tableData.add(tableRow);
        
        if(s.equals("week"))
            for (int i = 0; i < weeklytable.size(); i++) {
            Cell product=new Cell(f2, weeklytable.get(i).getName());
            Cell quantity=new Cell(f2,String.valueOf(weeklytable.get(i).getItemq()));
            Cell price=new Cell(f2,String.valueOf(weeklytable.get(i).getPrice()));
            Cell tr=new Cell(f2,String.valueOf(weeklytable.get(i).getPrice()*weeklytable.get(i).getItemq()));
            tableRow=new ArrayList<Cell>();
            tableRow.add(product);
            tableRow.add(price);
            tableRow.add(quantity);
            tableRow.add(tr);
            tableData.add(tableRow);
            }
        if(s.equals("month")){
            for (int i = 0; i < monthlytable.size(); i++) {
            Cell product=new Cell(f2, monthlytable.get(i).getName());
            Cell quantity=new Cell(f2,String.valueOf(monthlytable.get(i).getItemq()));
            Cell price=new Cell(f2,String.valueOf(monthlytable.get(i).getPrice()));
            Cell tr=new Cell(f2,String.valueOf(monthlytable.get(i).getPrice()*monthlytable.get(i).getItemq()));
            tableRow=new ArrayList<Cell>();
            tableRow.add(product);
            tableRow.add(quantity);
            tableRow.add(price);
            tableRow.add(tr);
            tableData.add(tableRow);                
            }
        }

            table.setData(tableData);
            table.setPosition(50f, 100f);
            table.setColumnWidth(0, 150f);
            table.setColumnWidth(1, 90f);
            table.setColumnWidth(2, 90f);
            table.setColumnWidth(3, 150f);
        while (true){
            table.drawOn(page);
            if (!table.hasMoreData()){
                table.resetRenderedPagesCount();
                break;
            }
                page=new Page(pdf, A4.PORTRAIT);
        }
                pdf.setTitle("Store Manager's Sales Report");
                pdf.setSubject("Store Manager");
                pdf.setAuthor("Store Manager");
                //pdf.complete();
                pdf.close();
                billout.close();
                Alert a=new Alert(AlertType.INFORMATION);
                a.setContentText("Saved to"+ bill.getAbsolutePath());
                a.show();
    }
   


}

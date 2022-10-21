module ia.storemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ia.storemanager to javafx.fxml;
    exports ia.storemanager;
    requires pdfjet;
}

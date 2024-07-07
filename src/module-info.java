module com.uno {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;



    opens controllers to javafx.fxml;
    opens models to javafx.fxml;

    exports models;
}
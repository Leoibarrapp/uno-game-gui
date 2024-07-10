module com.uno {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires com.google.gson;
    requires java.desktop;

    opens controllers to javafx.fxml;
    opens models to javafx.fxml;
    opens models.game to com.google.gson, javafx.base;

    exports models;
}
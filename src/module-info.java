module com.uno {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires com.google.gson;
    requires javafx.controls;

    opens controllers to javafx.fxml, com.google.gson;
    opens models to com.google.gson, javafx.base, javafx.fxml;
    opens models.game to com.google.gson, javafx.base;

    exports controllers to javafx.graphics;
    exports models to javafx.graphics;
}

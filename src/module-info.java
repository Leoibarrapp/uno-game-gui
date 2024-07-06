module com.uno {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports controllers;
    exports models;

    opens controllers to javafx.fxml;
    opens views to javafx.fxml;
    opens models.game;
    exports models.game;
}
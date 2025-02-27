module com.neoapps.app.snakegame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.management;


    opens com.neoapps.app.snakegame to javafx.fxml;
    exports com.neoapps.app.snakegame;
}
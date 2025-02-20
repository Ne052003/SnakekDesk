module com.neoapps.app.snakegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.neoapps.app.snakegame to javafx.fxml;
    exports com.neoapps.app.snakegame;
}
module com.example.loginscreen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.loginscreen to javafx.fxml;
    exports com.example.loginscreen;
}
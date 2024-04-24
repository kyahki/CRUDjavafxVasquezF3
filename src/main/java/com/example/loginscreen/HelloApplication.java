package com.example.loginscreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 540);
        scene.getStylesheets().add(getClass().getResource("user1.css").toExternalForm());
        stage.setTitle("Login Dog!!");
        scene.setFill(Color.GRAY);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        CreateTable.createTable();

        launch();
    }
}
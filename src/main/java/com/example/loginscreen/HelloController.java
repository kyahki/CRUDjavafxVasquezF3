package com.example.loginscreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    public Pane pnLogout,pnGoBack;
    public VBox pnLogin, pnRegister;

    public ColorPicker cpPicker;

    MenuItem menuItem1 = new MenuItem("DarkMode");
    MenuItem menuItem2 = new MenuItem("LightMode");


    private static final String[] validUsernames = {"user1", "user2", "user3"};
    private static final String[] validPasswords = {"pass1", "pass2", "pass3"};
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private TextField txtRegisterUser;
    @FXML
    private PasswordField txtRegisterPass;
    @FXML
    protected void onHelloButtonClick() throws IOException {
        String username = txtUser.getText();
        String password = txtPass.getText();
        boolean isValidCredentials = false;
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        for (int i = 0; i < validUsernames.length; i++) {
            if (username.equals(validUsernames[i]) && password.equals(validPasswords[i])) {
                isValidCredentials = true;
                break;
            }
            else{
                p.getScene().getStylesheets().clear();
                p.getScene().getStylesheets().add(getClass().getResource("user1.css").toExternalForm());
                Parent scene = FXMLLoader.load(getClass().getResource("incorrect.fxml"));
                scene.prefHeight(p.getScene().getHeight());
                scene.prefWidth(p.getScene().getWidth());
                p.getChildren().clear();
                p.getChildren().add(scene);
            }
        }
        if (isValidCredentials) {
//            AnchorPane p = (AnchorPane) pnLogin.getParent();
            p.getScene().getStylesheets().clear();
            p.getScene().getStylesheets().add(getClass().getResource("user1.css").toExternalForm());
            Parent scene = FXMLLoader.load(getClass().getResource("home-view.fxml"));
            scene.prefHeight(p.getScene().getHeight());
            scene.prefWidth(p.getScene().getWidth());
            p.getChildren().clear();
            p.getChildren().add(scene);
        }
    }
    @FXML
    protected void onRegisterClick() throws  IOException{
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getScene().getStylesheets().clear();
        p.getScene().getStylesheets().add(getClass().getResource("user1.css").toExternalForm());
        Parent scene = FXMLLoader.load(getClass().getResource("register.fxml"));
        scene.prefHeight(p.getScene().getHeight());
        scene.prefWidth(p.getScene().getWidth());
        p.getChildren().clear();
        p.getChildren().add(scene);
    }



//    menuItem1.setOnAction(new EventHandler<ActionEvent>() {
//        public void handle(ActionEvent event) {
//            System.out.println("Option 3 selected");
//        }
//    });

    //    protected void onSwitchClick ()throws IOException{
//        int counter = 0;
//        counter++;
//        if (counter == 1 ){
//
//
//        }else{
//
//
//        }
//    }
    @FXML
    protected  void onGoBack(ActionEvent actionEvent) throws IOException{
        AnchorPane p = (AnchorPane) pnGoBack.getParent();
        Parent scene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);

    }
    @FXML
    protected  void onLogout(ActionEvent actionEvent) throws IOException{
        Color color = cpPicker.getValue();

       String realColor = "rgb(" +
               (int)(color.getRed() * 255) + ", " +
               (int)(color.getGreen() * 255) + ", " +
        (int)(color.getBlue() * 255) + ")";

        AnchorPane p = (AnchorPane) pnLogout.getParent();
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(getClass().getResource("user1.css").getPath(),true));
            bw.write(".button { -fx-background-color: " + realColor + " }");
            bw.newLine();
            bw.close();
        }catch(IOException e){

        }
        Parent scene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);
    }

    @FXML
    protected void onCreateAccount(){
        String username = txtRegisterUser.getText();
        String password = txtRegisterPass.getText();
        AnchorPane p = (AnchorPane) pnRegister.getParent();
        try(Connection c = MySqlConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT into users (name, password) VALUES (?,?)")){

            statement.setString(1,username);
            statement.setString(2, password);
            int rows = statement.executeUpdate();

            if(rows > 0){
                System.out.println("Rows inserted: " + rows);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
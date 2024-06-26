package com.example.loginscreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    public static User user;
    public Pane pnLogout,pnGoBack,pnGoUpdate,pnUpdateDog;
    public VBox pnLogin, pnRegister,pnUpdate,pnDog;

    public ColorPicker cpPicker;


    @FXML
    private TableView<Dog> dogTableView;

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private TextField txtRegisterUser,txtNewUsername,txtNewDogName,txtNewDogAge,txtDogID;
    @FXML
    private PasswordField txtRegisterPass,txtNewPassword;

    public Label lblUpdate,lblUpdateDog;

    @FXML
    private TextField txtDogName,txtDogBreed,txtDogAge,txtDogGender,txtRemoveDogName;


    @FXML
    private void onHelloButtonClick() throws IOException {
        String username = txtUser.getText();
        String password = txtPass.getText();
        boolean isValidCredentials = false;
        AnchorPane p = (AnchorPane) pnLogin.getParent();

        int retrievedUserID = 0;
        try (Connection c = MySqlConnection.getConnection();
             Statement statement = c.createStatement()) {
            String query = "SELECT * FROM users";
            ResultSet res = statement.executeQuery(query);
//
            while (res.next()) {
                String username2 = res.getString("name");
                String password2 = res.getString("password");

                if (username.equals(username2) && password.equals(password2)) {
                    isValidCredentials = true;
                    retrievedUserID = res.getInt("id");
                    user = new User(retrievedUserID,username2,password2);
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isValidCredentials) {
            p = (AnchorPane) pnLogin.getParent();
            p.getScene().getStylesheets().clear();
            p.getScene().getStylesheets().add(getClass().getResource("user1.css").toExternalForm());
            Parent scene = FXMLLoader.load(getClass().getResource("updateAccount.fxml"));
            scene.prefHeight(p.getScene().getHeight());
            scene.prefWidth(p.getScene().getWidth());
            p.getChildren().clear();
            p.getChildren().add(scene);
        } else {
            p.getScene().getStylesheets().clear();
            p.getScene().getStylesheets().add(getClass().getResource("user1.css").toExternalForm());
            Parent scene = FXMLLoader.load(getClass().getResource("incorrect.fxml"));
            scene.prefHeight(p.getScene().getHeight());
            scene.prefWidth(p.getScene().getWidth());
            p.getChildren().clear();
            p.getChildren().add(scene);
        }

    }
//    @FXML
//    private void onReadAccount() throws IOException {
//        try (Connection c = MySqlConnection.getConnection();
//             Statement statement = c.createStatement()) {
//            String query = "SELECT * FROM users";
//            ResultSet res = statement.executeQuery(query);
//
//            // Clear existing items in the TableView
//            userTableView.getItems().clear();
//
//            while (res.next()) {
//                int id = res.getInt("id");
//                String name = res.getString("name");
//                String password = res.getString("password");
//
//                // Create a new row with the retrieved data
//                User user = new User(id, name, password);
//
//                // Add the row to the TableView
//                userTableView.getItems().add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
@FXML
private void onReadAccount() throws IOException {
    AnchorPane p = (AnchorPane) pnUpdate.getParent();
    Parent scene = FXMLLoader.load(getClass().getResource("readAcc.fxml"));
    p.getChildren().clear();
    p.getChildren().add(scene);

    try (Connection c = MySqlConnection.getConnection();
         Statement statement = c.createStatement()) {
        String query = "SELECT * FROM users";
        ResultSet res = statement.executeQuery(query);


        List<User> userList = new ArrayList<>();

        while (res.next()) {
            int id = res.getInt("id");
            String name = res.getString("name");
            String password = res.getString("password");


            userList.add(new User(id, name, password));
        }

        TableView<User> userTableView = new TableView<>();

        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(100);

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setPrefWidth(200);

        userTableView.getColumns().addAll(idColumn, nameColumn, passwordColumn);
        userTableView.setItems(FXCollections.observableArrayList(userList));


        userTableView.setPrefWidth(500);

        p.getChildren().add(userTableView);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}





//    private void populateUserListFromDatabase() {
//        userList.clear(); // Clear existing data
//
//        try (Connection c = MySqlConnection.getConnection();
//             Statement statement = c.createStatement()) {
//            String query = "SELECT * FROM users";
//            ResultSet res = statement.executeQuery(query);
//
//            while (res.next()) {
//                int id = res.getInt("id");
//                String username = res.getString("name");
//                String password = res.getString("password");
//                userList.add(new User(id, username, password));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }




    @FXML
    private void onRegisterClick() throws  IOException{
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getScene().getStylesheets().clear();
        p.getScene().getStylesheets().add(getClass().getResource("user1.css").toExternalForm());
        Parent scene = FXMLLoader.load(getClass().getResource("register.fxml"));
        scene.prefHeight(p.getScene().getHeight());
        scene.prefWidth(p.getScene().getWidth());
        p.getChildren().clear();
        p.getChildren().add(scene);
    }

    @FXML
    private void onUpdateUsername() {
        int currentUserId = user.getId();
        String newUsername = txtNewUsername.getText();
        UpdateData updateData = new UpdateData();
        try {
            updateData.updateUsername(currentUserId, newUsername);
            lblUpdate.setText("New username set successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        @FXML
        protected void onUpdatePassword() {
            int currentUserId = user.getId();
            String newPassword = txtNewPassword.getText();

            UpdateData updateData = new UpdateData();
            try {
                updateData.updatePassword(currentUserId, newPassword);
                lblUpdate.setText("New password set successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }

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
            // ^^^^^^^^^^^^^^^^^^
            //    p = (AnchorPane) pnLogin.getParent();
            //            p.getScene().getStylesheets().clear();
            //            p.getScene().getStylesheets().add(getClass().getResource("user1.css").toExternalForm());
            //    Parent scene = FXMLLoader.load(getClass().getResource("home-view.fxml"));
            //            scene.prefHeight(p.getScene().getHeight());
            //            scene.prefWidth(p.getScene().getWidth());
            //            p.getChildren().clear();
            //            p.getChildren().add(scene);
    @FXML
    private void onLogout() throws IOException {
        AnchorPane p = (AnchorPane) pnUpdate.getParent();
        Parent scene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);
    }
    @FXML
    private void onGoBack(ActionEvent actionEvent) throws IOException{
        AnchorPane p = (AnchorPane) pnGoBack.getParent();
        Parent scene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);

    }
    @FXML
    private void onGoBack2(ActionEvent actionEvent) throws IOException{
        AnchorPane p = (AnchorPane) pnGoUpdate.getParent();
        Parent scene = FXMLLoader.load(getClass().getResource("updateAccount.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);

    }
    @FXML
    private void onGoBack3(ActionEvent actionEvent) throws IOException{
        AnchorPane p = (AnchorPane) pnDog.getParent();
        Parent scene = FXMLLoader.load(getClass().getResource("updateAccount.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);

    }
    @FXML
    private void onGoBack4(ActionEvent actionEvent) throws IOException{
        AnchorPane p = (AnchorPane) pnUpdateDog.getParent();
        Parent scene = FXMLLoader.load(getClass().getResource("showDogs.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);

    }
//    @FXML
//    private  void onLogout(ActionEvent actionEvent) throws IOException{
//        Color color = cpPicker.getValue();
//
//       String realColor = "rgb(" +
//               (int)(color.getRed() * 255) + ", " +
//               (int)(color.getGreen() * 255) + ", " +
//        (int)(color.getBlue() * 255) + ")";
//
//        AnchorPane p = (AnchorPane) pnLogout.getParent();
//        try{
//            BufferedWriter bw = new BufferedWriter(new FileWriter(getClass().getResource("user1.css").getPath(),true));
//            bw.write(".button { -fx-background-color: " + realColor + " }");
//            bw.newLine();
//            bw.close();
//        }catch(IOException e){
//
//        }
//        Parent scene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
//        p.getChildren().clear();
//        p.getChildren().add(scene);
//    }

    @FXML
    private void onCreateAccount() throws IOException {
        String username = txtRegisterUser.getText();
        String password = txtRegisterPass.getText();
        AnchorPane p;

        try (Connection c = MySqlConnection.getConnection()) {
            c.setAutoCommit(false);

            try (PreparedStatement statement = c.prepareStatement(
                    "INSERT into users (name, password) VALUES (?,?)")) {
                statement.setString(1, username);
                statement.setString(2, password);
                int rows = statement.executeUpdate();

                if (rows > 0) {
                    System.out.println("Rows inserted: " + rows);
                }

                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw new RuntimeException("Transaction failed.", e);
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error.", e);
        }

        p = (AnchorPane) pnRegister.getParent();
        Parent scene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);
    }

    @FXML
    private void onDeleteAccount() throws IOException {
        AnchorPane p = (AnchorPane) pnUpdate.getParent();
        try(Connection c = MySqlConnection.getConnection();
        PreparedStatement statement = c.prepareStatement(
                "DELETE FROM users WHERE id=? RETURNING *"
        )){
            int id = user.getId();
            statement.setInt(1,id);
            int rows = statement.executeUpdate();
            ResultSet res = statement.getResultSet();
            if(res.next()){
                System.out.println("User successfully deleted!");
                System.out.println("Name: " + res.getString("name"));
                System.out.println("Password: "+ res.getString("password"));
            }
            System.out.println("Rows Deleted: " + rows);


        }catch (SQLException e){
            e.printStackTrace();
        }
        Parent scene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);
    }
//    @FXML
//    public void onCreateDog() throws IOException {
//        AnchorPane p = (AnchorPane) pnUpdate.getParent();
//        CreateTable.createDogTable();
//        Parent scene = FXMLLoader.load(getClass().getResource("showDogs.fxml"));
//        p.getChildren().clear();
//        p.getChildren().add(scene);
//    }

    @FXML
    public void onAddDog() throws IOException {
        String dogName = txtDogName.getText();
        String dogBreed = txtDogBreed.getText();
        int dogAge = Integer.parseInt(txtDogAge.getText());
        String dogGender = txtDogGender.getText();

        int userId = user.getId();

        try (Connection connection = MySqlConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO dogprofile (id, dogname, dogbreed, dogage, gender) VALUES (?, ?, ?, ?, ?)")) {

                statement.setInt(1, userId);
                statement.setString(2, dogName);
                statement.setString(3, dogBreed);
                statement.setInt(4, dogAge);
                statement.setString(5, dogGender);
                int rows = statement.executeUpdate();

                if (rows > 0) {
                    System.out.println("Rows inserted: " + rows);
                    lblUpdateDog.setText("Dog succesfully Added!");
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Transaction failed.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error.", e);
        }
    }

    @FXML
    private void onCreateDog() throws IOException {
        AnchorPane p = (AnchorPane) pnUpdate.getParent();
        CreateTable.createDogTable();
        Parent scene = FXMLLoader.load(getClass().getResource("showDogs.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);
    }

    @FXML
    private void onShowDogs() {
        List<Dog> dogs = new ArrayList<>();

        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM dogprofile WHERE id = ?")) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int dogId = resultSet.getInt("dogid");
                String dogName = resultSet.getString("dogname");
                String dogBreed = resultSet.getString("dogbreed");
                int dogAge = resultSet.getInt("dogage");
                String dogGender = resultSet.getString("gender");

                Dog dog = new Dog(dogId,user.getId() ,dogName, dogBreed, dogAge, dogGender);
                dogs.add(dog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList<Dog> dogList = FXCollections.observableArrayList(dogs);
        lblUpdateDog.setText("Showing current Dogs!");
        dogTableView.setItems(dogList);
    }
    @FXML
    private void onRemoveDog() {
        String dogToRemove = txtRemoveDogName.getText();

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "DELETE FROM dogprofile WHERE dogname = ? AND id = ? RETURNING *")) {
            statement.setString(1, dogToRemove);
            statement.setInt(2, user.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                lblUpdateDog.setText("Dog successfully deleted!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onUpdateDog() throws IOException {
        AnchorPane p = (AnchorPane) pnDog.getParent();
        CreateTable.createDogTable();
        Parent scene = FXMLLoader.load(getClass().getResource("updateDogs.fxml"));
        p.getChildren().clear();
        p.getChildren().add(scene);
    }
    @FXML
    private void onUpdateDogName() {
        int dogIdToUpdate = Integer.parseInt(txtDogID.getText());
        String newDogName = txtNewDogName.getText();
        UpdateData updateData = new UpdateData();
        try {
            updateData.updateDogName(dogIdToUpdate, newDogName);
            lblUpdate.setText("New dog name set successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpdateDogAge() {
        int dogIdToUpdate = Integer.parseInt(txtDogID.getText());
        int newDogAge = Integer.parseInt(txtNewDogAge.getText());
        UpdateData updateData = new UpdateData();
        try {
            updateData.updateDogAge(dogIdToUpdate, newDogAge);
            lblUpdate.setText("New dog age set successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
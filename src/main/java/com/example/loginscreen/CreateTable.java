package com.example.loginscreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void main(String[] args) {
        try(Connection c = MySqlConnection.getConnection();
            Statement statement = c.createStatement()){

                String query = "CREATE TABLE users (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(50) NOT NULL," +
                        "password VARCHAR(100) NOT NULL)";
                statement.execute(query);
            System.out.println("Table created successfully!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void createTable() {
        try(Connection c = MySqlConnection.getConnection();
            Statement statement = c.createStatement()){

            String query = "CREATE TABLE users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "password VARCHAR(100) NOT NULL)";
            statement.execute(query);
            System.out.println("Table created successfully!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void createDogTable() {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("CREATE TABLE IF NOT EXISTS dogprofile (" +
                     "dogid INT AUTO_INCREMENT PRIMARY KEY," +
                     "id INT NOT NULL," +
                     "FOREIGN KEY (id) REFERENCES users(id)," +
                     "dogname VARCHAR(50) NOT NULL," +
                     "dogbreed VARCHAR(100) NOT NULL," +
                     "dogage INT NOT NULL," +
                     "gender VARCHAR(50) NOT NULL" +
                     ")")) {
            statement.execute();
            System.out.println("Dog Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

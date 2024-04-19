package com.example.loginscreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
    public static void main(String[] args) {
        try(Connection c = MySqlConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "UPDATE users SET name=? WHERE id=?"
            )){
            String name = "Tisumi";
            int id = 1;
            statement.setInt(2, id);
            statement.setString(1, name);
            int rows = statement.executeUpdate();
            if(rows > 0){
                System.out.println("Rows updated: "+ rows);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

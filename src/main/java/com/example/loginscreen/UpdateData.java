package com.example.loginscreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {

    public void updateUsername(int userIdToUpdate, String newUsername) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE users SET name = ? WHERE id = ?")) {

            preparedStatement.setString(1, newUsername);
            preparedStatement.setInt(2, userIdToUpdate);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data updated successfully!");
            }
        }
    }

    public void updatePassword(int userIdToUpdate, String newPassword) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE users SET password = ? WHERE id = ?")) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userIdToUpdate);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully!");
            }
        }
    }
    public void updateDogName(int dogIdToUpdate, String newDogName) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE dogprofile SET dogname = ? WHERE dogid = ?")) {

            preparedStatement.setString(1, newDogName);
            preparedStatement.setInt(2, dogIdToUpdate);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Dog name updated successfully!");
            }
        }
    }

    public void updateDogAge(int dogIdToUpdate, int newDogAge) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE dogprofile SET dogage = ? WHERE dogid = ?")) {

            preparedStatement.setInt(1, newDogAge);
            preparedStatement.setInt(2, dogIdToUpdate);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Dog age updated successfully!");
            }
        }
    }

}
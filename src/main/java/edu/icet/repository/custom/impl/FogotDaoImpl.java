package edu.icet.repository.custom.impl;

import edu.icet.dbconnection.DBConnection;
import edu.icet.entity.UserEntity;
import edu.icet.repository.custom.FogotDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FogotDaoImpl implements FogotDao {
    @Override
    public boolean save(UserEntity entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(UserEntity entity) throws SQLException {
        return false;
    }

    @Override
    public UserEntity search(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public List<UserEntity> getAll() {
        return List.of();
    }

    @Override
    public UserEntity checkemailrepeat(String email) {
        System.out.println("minidu");
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email); // Fix: Changed index from 2 to 1
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(resultSet.getString("id"));
                userEntity.setEmail(resultSet.getString("email"));
                userEntity.setPassword(resultSet.getString("password"));
                return userEntity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public UserEntity updatePassword(String email, String newPassword) {
        System.out.println("alwis");

        // First, check if the email exists in the database
        String checkEmailQuery = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement checkEmailStatement = connection.prepareStatement(checkEmailQuery)) {

            checkEmailStatement.setString(1, email);
            ResultSet resultSet = checkEmailStatement.executeQuery();

            if (!resultSet.next()) {
                // Email not found, return null
                return null;
            }

            // If email exists, proceed to update the password
            String updatePasswordQuery = "UPDATE users SET password = ? WHERE email = ?";
            try (PreparedStatement updatePasswordStatement = connection.prepareStatement(updatePasswordQuery)) {
                updatePasswordStatement.setString(1, newPassword);
                updatePasswordStatement.setString(2, email);

                int rowsUpdated = updatePasswordStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    // Fetch updated user details after password update
                    String selectQuery = "SELECT * FROM users WHERE email = ?";
                    try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                        selectStatement.setString(1, email);
                        ResultSet updatedResultSet = selectStatement.executeQuery();

                        if (updatedResultSet.next()) {
                            UserEntity userEntity = new UserEntity();
                            userEntity.setId(updatedResultSet.getString("id"));
                            userEntity.setUsername(updatedResultSet.getString("username"));
                            userEntity.setEmail(updatedResultSet.getString("email"));
                            userEntity.setPassword(updatedResultSet.getString("password"));
                            return userEntity;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}





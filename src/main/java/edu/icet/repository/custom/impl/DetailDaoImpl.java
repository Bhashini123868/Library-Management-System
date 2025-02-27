package edu.icet.repository.custom.impl;

import edu.icet.dbconnection.DBConnection;
import edu.icet.dto.Book;
import edu.icet.repository.custom.DetailDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DetailDaoImpl implements DetailDao {
    @Override
    public boolean save(Book entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Book entity) throws SQLException {
        return false;
    }

    @Override
    public Book search(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Integer getCustomerCount() {
        String query="SELECT COUNT(*) FROM member";
        try (Connection connection= DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             ResultSet resultSet=preparedStatement.executeQuery()) {
            if(resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer getBookCount() {
        String query="SELECT COUNT(*) FROM book";
        try (Connection connection= DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             ResultSet resultSet=preparedStatement.executeQuery()) {
            if(resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Double getFineCount() {
        String query="SELECT SUM(fineAmount) FROM fine";
        try (Connection connection=DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             ResultSet resultSet=preparedStatement.executeQuery()) {
            if(resultSet.next()) return resultSet.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public Integer getOverDueBooksCount() {
        String query="SELECT COUNT(*) FROM borrow WHERE ReturnDate<CURDATE() AND IsReturn=0";
        try (Connection connection=DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             ResultSet resultSet=preparedStatement.executeQuery()) {
            if(resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer getUnpaidFineCount() {
        String query="SELECT COUNT(*) FROM borrow WHERE DateGiven IS NULL";
        try (Connection connection=DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             ResultSet resultSet=preparedStatement.executeQuery()) {
            if(resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

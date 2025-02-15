package controller.borrowAndReturn;

import db.Db_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BorrowAndReturn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BorrowAndReturnController implements BorrowAndReturnService {
    private static BorrowAndReturnController instance;

    private BorrowAndReturnController() { }

    public static BorrowAndReturnController getInstance() {
        if (instance == null) instance = new BorrowAndReturnController();
        return instance;
    }

    @Override
    public List<BorrowAndReturn> getAll() {
        try (Connection connection = Db_Connection.getInstance().getConnection()) {
            String query = "SELECT * FROM borrow_return";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            ObservableList<BorrowAndReturn> borrowAndReturnsObList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                borrowAndReturnsObList.add(new BorrowAndReturn(
                        resultSet.getString("recordID"),
                        resultSet.getString("userID"),
                        resultSet.getString("bookID"),
                        resultSet.getString("borrowDate"),
                        resultSet.getString("returnDate"),
                        resultSet.getString("fine")
                ));
            }
            return borrowAndReturnsObList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateBorrowAndReturn(BorrowAndReturn borrowAndReturn) {
        String sql = "UPDATE borrow_return SET userID=?, bookID=?, borrowDate=?, returnDate=?, fine=? WHERE recordID=?";
        try (Connection connection = Db_Connection.getInstance().getConnection()) {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, borrowAndReturn.getUserID());
            stm.setString(2, borrowAndReturn.getBookID());
            stm.setString(3, borrowAndReturn.getBorrowDate());
            stm.setString(4, borrowAndReturn.getReturnDate());
            stm.setString(5, borrowAndReturn.getFine());
            stm.setString(6, borrowAndReturn.getRecordID());
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteBorrowAndReturn(String recordID) {
        String sql = "DELETE FROM borrow_return WHERE recordID=?";
        try (Connection connection = Db_Connection.getInstance().getConnection()) {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, recordID);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BorrowAndReturn searchBorrowAndReturn(String recordID) {
        String sql = "SELECT * FROM borrow_return WHERE recordID=?";
        try (Connection connection = Db_Connection.getInstance().getConnection()) {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, recordID);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                return new BorrowAndReturn(
                        resultSet.getString("recordID"),
                        resultSet.getString("userID"),
                        resultSet.getString("bookID"),
                        resultSet.getString("borrowDate"),
                        resultSet.getString("returnDate"),
                        resultSet.getString("fine")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveBorrowAndReturn(BorrowAndReturn borrowAndReturn) {
        String sql = "INSERT INTO borrow_return (recordID, userID, bookID, borrowDate, returnDate, fine) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = Db_Connection.getInstance().getConnection()) {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, borrowAndReturn.getRecordID());
            stm.setString(2, borrowAndReturn.getUserID());
            stm.setString(3, borrowAndReturn.getBookID());
            stm.setString(4, borrowAndReturn.getBorrowDate());
            stm.setString(5, borrowAndReturn.getReturnDate());
            stm.setString(6, borrowAndReturn.getFine());
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

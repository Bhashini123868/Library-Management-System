package edu.icet.repository.custom.impl;

import edu.icet.dbconnection.DBConnection;
import edu.icet.entity.BookEntity;
import edu.icet.entity.BorrowBooksEntity;
import edu.icet.entity.FineEntity;
import edu.icet.entity.MemberEntity;
import edu.icet.repository.custom.BorrowDao;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowDaoImpl implements BorrowDao {

    private static final String SELECT_MEMBER_BY_ID_SQL = "SELECT * FROM member WHERE MemberId = ?";
    private static final String SELECT_BOOK_BY_ID_SQL = "SELECT * FROM book WHERE BookId = ? AND Availability = 'Available'";


    @Override
    public boolean save(BookEntity entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(BookEntity entity) throws SQLException {
        return false;
    }

    @Override
    public BookEntity search(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public List<BookEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public BookEntity searchBookById(String bookId) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID_SQL)) {

            preparedStatement.setString(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new BookEntity(
                        resultSet.getString("BookId"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("BookTitle"),
                        resultSet.getString("Author"),
                        resultSet.getString("Availability")
                );
            }
        }
        return null;
    }

    @Override
    public MemberEntity searchById(String memberId) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEMBER_BY_ID_SQL)) {

            preparedStatement.setString(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new MemberEntity(
                        resultSet.getString("MemberId"),
                        resultSet.getString("Name"),
                        resultSet.getString("ContactNumber"),
                        resultSet.getDate("MembershipDate").toLocalDate()
                );
            }
        }
        return null;
    }

    @Override
    public boolean saveTheRecord(BorrowBooksEntity borrowRecord, Connection connection) throws SQLException {
        String sql = "INSERT INTO borrow (BorrowId, MemberId, BookId, BorrowedDate, ReturnDate, DateGiven, IsReturn) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, borrowRecord.getBorrowId());
            pstm.setString(2, borrowRecord.getMemberId());
            pstm.setString(3, borrowRecord.getBookId());
            pstm.setDate(4, Date.valueOf(borrowRecord.getBorrowDate()));
            pstm.setDate(5, Date.valueOf(borrowRecord.getReturnDate()));
            pstm.setDate(6, null);
            pstm.setBoolean(7, borrowRecord.isReturned());

            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public String getLastBorrowId(Connection connection) throws SQLException {
        String sql = "SELECT BorrowID FROM borrow ORDER BY BorrowId DESC LIMIT 1";

        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("BorrowId");
            }
        }
        return null;
    }

    @Override
    public boolean hasUnreturnedBooks(String memberId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM borrow WHERE MemberId = ? AND IsReturn = 0";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, memberId);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    @Override
    public List<BorrowBooksEntity> getBorrowRecordsByMemberId(String memberId) throws SQLException {
        String sql = "SELECT * FROM borrow WHERE MemberId = ? AND IsReturn = 0";
        List<BorrowBooksEntity> borrowRecords = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, memberId);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                BorrowBooksEntity borrowRecord = new BorrowBooksEntity(
                        resultSet.getString("BorrowId"),
                        resultSet.getString("MemberId"),
                        resultSet.getString("BookId"),
                        resultSet.getDate("BorrowedDate").toLocalDate(),
                        resultSet.getDate("ReturnDate").toLocalDate(),
                        resultSet.getDate("DateGiven") != null ? resultSet.getDate("DateGiven").toLocalDate() : null,
                        resultSet.getBoolean("IsReturn")
                );
                borrowRecords.add(borrowRecord);
            }
        }
        return borrowRecords;
    }

    @Override
    public BorrowBooksEntity getBorrowRecordByBookId(String selectedBookId) throws SQLException {
        String sql = "SELECT * FROM borrow WHERE BookId = ? AND IsReturn = 0";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, selectedBookId);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                return new BorrowBooksEntity(
                        resultSet.getString("BorrowId"),
                        resultSet.getString("MemberId"),
                        resultSet.getString("BookId"),
                        resultSet.getDate("BorrowedDate").toLocalDate(),
                        resultSet.getDate("ReturnDate").toLocalDate(),
                        resultSet.getDate("DateGiven") != null ? resultSet.getDate("DateGiven").toLocalDate() : null,
                        resultSet.getBoolean("IsReturn")
                );
            }
        }
        return null;
    }

    @Override
    public boolean updateBorrowRecordAsReturned(String selectedBookId, LocalDate today, Connection connection) {
        String sql = "UPDATE borrow SET IsReturn = 1, DateGiven = ? WHERE BookId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setDate(1, Date.valueOf(today));
            pstm.setString(2, selectedBookId);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBorrowIdByBookId(String selectedBookId, Connection connection) throws SQLException {

        String sql = "SELECT BorrowId FROM borrow WHERE BookId = ? AND IsReturn = 0";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, selectedBookId);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("BorrowId");
            }
        }
        return null;

    }

    @Override
    public boolean saveFine(FineEntity fine, Connection connection) throws SQLException {
        String sql = "INSERT INTO fine (borrowId, fineAmount, paymentDate) VALUES (?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, fine.getBorrowId());
            pstm.setBigDecimal(2, fine.getFineAmount());
            pstm.setDate(3, Date.valueOf(fine.getPaymentDate()));

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting fine: " + e.getMessage());
            throw e;
        }
    }

}
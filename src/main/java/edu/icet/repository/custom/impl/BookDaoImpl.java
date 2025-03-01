package edu.icet.repository.custom.impl;

import edu.icet.dbconnection.DBConnection;
import edu.icet.entity.BookEntity;
import edu.icet.repository.custom.BookDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private static final String SELECT_LAST_BOOK_ID_SQL = "SELECT BookId FROM book ORDER BY BookId DESC LIMIT 1";
    private static final String INSERT_BOOK_SQL =
            "INSERT INTO book (BookId,  BookTitle,ISBN , Availability, CategoryId, Author ) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_BOOK_SQL = "SELECT * FROM book";
    private static final String DELETE_BOOK_SQL = "DELETE FROM book WHERE BookId = ?";
    private static final String UPDATE_BOOK_SQL =
            "UPDATE book SET BookTitle = ?, ISBN = ?, Availability = ?, CategoryId = ?, Author = ? WHERE BookId = ?";
    private static final String SELECT_BOOK_BY_ID_SQL = "SELECT * FROM book WHERE BookId = ?";



    @Override
    public boolean save(BookEntity entity) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_SQL)) {

            preparedStatement.setString(1, entity.getBookId());
            preparedStatement.setString(2, entity.getBookTitle());
            preparedStatement.setString(3, entity.getIsbn());
            preparedStatement.setString(4, entity.getAvailability());
            preparedStatement.setInt(5, entity.getCategoryId());
            preparedStatement.setString(6, entity.getAuthor());


            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean update(BookEntity entity) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_SQL)) {

            preparedStatement.setString(1, entity.getBookId());
            preparedStatement.setString(2, entity.getBookTitle());
            preparedStatement.setString(3, entity.getIsbn());
            preparedStatement.setString(4, entity.getAvailability());
            preparedStatement.setInt(5, entity.getCategoryId());
            preparedStatement.setString(6, entity.getAuthor());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }

    }

    @Override
    public BookEntity search(String s) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID_SQL)) {

            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new BookEntity(
                        resultSet.getString("BookId"),
                        resultSet.getString("BookTitle"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Availability"),
                        resultSet.getInt("CategoryId"),
                        resultSet.getString("Author")
                );
            }
        }
        return null;

    }

    @Override
    public boolean delete(String s) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_SQL)) {

            preparedStatement.setString(1, s);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public List<BookEntity> getAll() throws SQLException {
        List<BookEntity> books = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOK_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(new BookEntity(
                        resultSet.getString("BookId"),
                        resultSet.getString("BookTitle"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Availability"),
                        resultSet.getInt("CategoryId"),
                        resultSet.getString("Author")
                ));
            }
        }
        return books;
    }

    @Override
    public ArrayList<String> getAllCategoryIds() throws SQLException {
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select CategoryId From category");
        ArrayList<String> cateids=new ArrayList<>();
        while(rst.next()){
            cateids.add(rst.getString(1));
        }
        return cateids;
    }

    @Override
    public String getLastBookID() {
        String lastMemberID = "B0000";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_BOOK_ID_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastMemberID = resultSet.getString("BookId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lastMemberID;

    }

    @Override
    public boolean updateAvailabilityStatus(String bookId, String borrowed, Connection connection) throws SQLException {
        String sql = "UPDATE book SET Availability = ? WHERE BookId = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, borrowed);
            pstm.setString(2, bookId);

            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public Integer getCategoryIdByName(String categoryName) throws SQLException {
        String query = "SELECT CategoryId FROM category WHERE CategoryName = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("CategoryId");
            }
        }
        return null;
    }

}

package controller.book;

import db.Db_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;

import java.sql.*;
import java.util.List;

public class BookManagementController implements BookManagementService{
    private static BookManagementController instance;
    private BookManagementController(){

    }

    public static BookManagementController getInstance() {
        if (instance==null) instance = new BookManagementController();
        return instance;
    }

    @Override
    public List<Book> getAll() throws ClassNotFoundException, SQLException {
        Statement statement = Db_Connection.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Books");
        ObservableList<Book> bookObList = FXCollections.observableArrayList();
        while (resultSet.next()){
            bookObList.add(new Book(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getBoolean(6)
            ));
        }
        return bookObList;
    }

    @Override
    public boolean saveBook(Book book) throws SQLException {
        Connection connection = Db_Connection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Books VALUES (?,?,?,?,?,?)");
        stm.setObject(1,book.getBookID());
        stm.setObject(2,book.getISBN());
        stm.setObject(3,book.getTitle());
        stm.setObject(4,book.getAuthor());
        stm.setObject(5,book.getGenre());
        stm.setObject(6,book.getAvailability());
        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean updateBook(Book book) throws SQLException {
        Connection connection = Db_Connection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Update Books set ISBN=?, Title=?, Author=?, Genre=?, availability=? where book_id=?");
        stm.setObject(1,book.getISBN());
        stm.setObject(2,book.getTitle());
        stm.setObject(3,book.getAuthor());
        stm.setObject(4,book.getGenre());
        stm.setObject(5,book.getAvailability());
        stm.setObject(6,book.getBookID());
        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteBook(String BookID) throws SQLException {
        Connection connection = Db_Connection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Delete from Books where book_id=?");
        stm.setString(1,BookID);
        return stm.executeUpdate() > 0;
       
    }
    @Override
    public Book searchBook(String BookID) throws SQLException {
        Connection connection = Db_Connection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Select * from Books where book_id=?");
        stm.setObject(1,BookID);
        ResultSet resultSet = stm.executeQuery();
        if (resultSet.next()){
            Book book = new Book(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getBoolean(6)
            );
            return book;
        }
        return null;
    }
}

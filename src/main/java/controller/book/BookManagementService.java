package controller.book;

import model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookManagementService {
    List<Book> getAll() throws ClassNotFoundException, SQLException;
    boolean saveBook(Book book) throws SQLException;
    boolean updateBook(Book book) throws SQLException;
    boolean deleteBook(String BookID) throws SQLException;
    Book searchBook(String BookID) throws SQLException;
}

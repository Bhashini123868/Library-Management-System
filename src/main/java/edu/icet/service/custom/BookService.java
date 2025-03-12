package edu.icet.service.custom;

import edu.icet.dto.Book;
import edu.icet.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface  BookService extends SuperService {

    boolean addBook(Book book) throws SQLException;

    String showtheId();

    List<Book> getAllBooks() throws SQLException;

    boolean deleteMember(String bookId) throws SQLException;

    boolean updateBook(Book book) throws SQLException;

    Book searchBookById(String bookId) throws SQLException;


}

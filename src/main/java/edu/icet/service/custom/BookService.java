package edu.icet.service.custom;

import edu.icet.dto.Book;
import edu.icet.service.SuperService;
import edu.icet.dto.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BookService extends SuperService {
    List<String> getAllCategoryIds() throws SQLException;

    Integer getCategoryIdByName(String categoryName) throws SQLException;

    boolean addBook(Book book) throws SQLException;

    String showtheId();

    List<Book> getAllBooks() throws SQLException;

    boolean deleteMember(String bookId) throws SQLException;

    boolean updateBook(Book book) throws SQLException;

    Book searchBookById(String bookId) throws SQLException;

    List<Category> getAllCategories() throws SQLException;
}

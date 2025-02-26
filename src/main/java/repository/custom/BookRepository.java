package repository.custom;

import model.Book;
import java.util.List;

public interface BookRepository {
    List<Book> getAll();
    Book searchBooks(String bookID);
    boolean updateBook(Book book);
    boolean deleteBook(String bookID);
    boolean addBook(Book book);
}

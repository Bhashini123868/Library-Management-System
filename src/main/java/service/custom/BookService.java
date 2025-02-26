package service.custom;

import model.Book;
import service.SuperService;
import java.util.List;

public interface BookService extends SuperService {
    List<Book> getAll();
    Book searchBook(String bookID);
    boolean updateBook(Book book);
    boolean deleteBook(String bookID);
    boolean addBook(Book book);
}

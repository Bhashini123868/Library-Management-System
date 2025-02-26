package repository.custom.impl;

import model.Book;
import repository.custom.BookRepository;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private List<Book> bookList = new ArrayList<>();


    @Override
    public List<Book> getAll() {
        return new ArrayList<>(bookList);
    }

    @Override
    public Book searchBooks(String bookID) {
        return bookList.stream()
                .filter(book -> book.getBookID().equals(bookID))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateBook(Book book) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookID().equals(book.getBookID())) {
                bookList.set(i, book);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteBook(String bookID) {
        return bookList.removeIf(book -> book.getBookID().equals(bookID));
    }

    @Override
    public boolean addBook(Book book) {
        return bookList.add(book);
    }
}

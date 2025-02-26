package service.custom.impl;

import model.Book;
import repository.RepositoryFactory;
import repository.custom.BookRepository;
import service.custom.BookService;
import util.RepositoryType;

import java.util.List;

public class BookServiceImpl implements BookService {
    BookRepository bookRepository = RepositoryFactory.getInstance().getRepository_Factory(RepositoryType.Book);

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public Book searchBook(String bookID) {
        return bookRepository.searchBooks(bookID);
    }

    @Override
    public boolean updateBook(Book book) {
        return bookRepository.updateBook(book);
    }

    @Override
    public boolean deleteBook(String bookID) {
        return bookRepository.deleteBook(bookID);
    }

    @Override
    public boolean addBook(Book book) {
        return bookRepository.addBook(book);
    }
}

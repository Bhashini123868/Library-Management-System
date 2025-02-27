package edu.icet.service.custom.impl;

import edu.icet.dto.Book;
import edu.icet.entity.BookEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.BookDao;
import edu.icet.service.custom.BookService;
import edu.icet.util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private static BookServiceImpl instance;

    private BookServiceImpl(){}

    public static BookServiceImpl getInstance(){
        return  instance==null?new BookServiceImpl():instance;
    }

    BookDao bookDao= DaoFactory.getInstance().getDaoType(DaoType.BOOK);

    @Override
    public ArrayList<String> getAllCategoryIds() throws SQLException {
        return bookDao.getAllCategoryIds();

    }

    @Override
    public boolean addBook(Book book) throws SQLException {
        String lastMemberID = bookDao.getLastBookID();
        String newBookID = generateNextMemberID(lastMemberID);

        BookEntity bookEntity = new BookEntity(
                newBookID,
                book.getIsbn(),
                book.getBookTitle(),
                book.getAuthor(),
                book.getCategoryId(),
                book.getAvailability()
        );

        boolean isAdded = bookDao.save(bookEntity);
        return isAdded;
    }

    @Override
    public String showtheId() {
        return generateNextMemberID(bookDao.getLastBookID());
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<BookEntity> books = bookDao.getAll();
        return books.stream()
                .map(book -> new Book(
                        book.getBookId(),
                        book.getIsbn(),
                        book.getBookTitle(),
                        book.getAuthor(),
                        book.getCategoryId(),
                        book.getAvailability()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteMember(String bookId) throws SQLException {
        return bookDao.delete(bookId);
    }

    @Override
    public boolean updateBook(Book book) throws SQLException {
        BookEntity bookEntity = convertToEntity(book);
        return bookDao.update(bookEntity);
    }

    @Override
    public Book searchBookById(String bookId) throws SQLException {
        BookEntity bookEntity = bookDao.search(bookId);
        if (bookEntity == null) {
            return null;
        }
        return new Book(bookEntity.getBookId(), bookEntity.getIsbn(), bookEntity.getBookTitle(),
                bookEntity.getAuthor(), bookEntity.getCategoryId(), bookEntity.getAvailability());

    }

    private String generateNextMemberID(String lastMemberID) {
        int lastNumber = Integer.parseInt(lastMemberID.substring(1));
        int nextNumber = lastNumber + 1;
        return String.format("B%04d", nextNumber);
    }

    private BookEntity convertToEntity(Book book) {
        return new BookEntity(
                book.getBookId(),
                book.getIsbn(),
                book.getBookTitle(),
                book.getAuthor(),
                book.getCategoryId(),
                book.getAvailability()
        );
    }

}

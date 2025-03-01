package edu.icet.service.custom.impl;

import edu.icet.dbconnection.DBConnection;
import edu.icet.dto.Book;
import edu.icet.dto.BorrowBooks;
import edu.icet.dto.Fine;
import edu.icet.dto.Member;
import edu.icet.entity.BookEntity;
import edu.icet.entity.BorrowBooksEntity;
import edu.icet.entity.FineEntity;
import edu.icet.entity.MemberEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.BookDao;
import edu.icet.repository.custom.BorrowDao;
import edu.icet.service.custom.BorrowService;
import edu.icet.util.DaoType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowServiceImpl implements BorrowService {

    BorrowDao borrowDao = DaoFactory.getInstance().getDaoType(DaoType.BORROW);
    BookDao bookDao=DaoFactory.getInstance().getDaoType(DaoType.BOOK);

    private static BorrowServiceImpl instance;

    private BorrowServiceImpl() {
    }

    public static BorrowServiceImpl getInstance() {
        return instance == null ? new BorrowServiceImpl() : instance;
    }

    @Override
    public Member searchMemberById(String memberId) throws SQLException {
        MemberEntity memberEntity = borrowDao.searchById(memberId);
        if (memberEntity == null) {
            return null;
        }
        return new Member(memberEntity.getId(), memberEntity.getName(), memberEntity.getContact(), memberEntity.getDate());

    }

    @Override
    public Book searchBookById(String bookId) throws SQLException {
        BookEntity bookEntity = borrowDao.searchBookById(bookId);
        if (bookEntity == null) {
            return null;
        }
        return new Book(bookEntity.getBookId(), bookEntity.getIsbn(), bookEntity.getBookTitle(),
                bookEntity.getAuthor(), bookEntity.getCategoryId(), bookEntity.getAvailability());
    }

    @Override
    public boolean saveBorrowRecord(String memberId, String bookId, LocalDate borrowDate, LocalDate returnDate) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String borrowId = generateNextBorrowId();

            BorrowBooksEntity borrowRecord = new BorrowBooksEntity(
                    borrowId,
                    memberId,
                    bookId,
                    borrowDate,
                    returnDate,
                    null,
                    false
            );
            boolean isBorrowRecordSaved = borrowDao.saveTheRecord(borrowRecord, connection);

            boolean isBookAvailabilityUpdated = bookDao.updateAvailabilityStatus(bookId, "Borrowed", connection);

            if (isBorrowRecordSaved && isBookAvailabilityUpdated) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public boolean hasUnreturnedBooks(String memberId) throws SQLException {
        return borrowDao.hasUnreturnedBooks(memberId);
    }

    @Override
    public List<BorrowBooks> getBorrowRecordsByMemberId(String memberId) throws SQLException {
        List<BorrowBooksEntity> borrowRecordsByMemberId = borrowDao.getBorrowRecordsByMemberId(memberId);
        List<BorrowBooks> borrowRecordsList = new ArrayList<>();

        for (BorrowBooksEntity entity : borrowRecordsByMemberId) {
            BorrowBooks record = new BorrowBooks();
            record.setBorrowId(entity.getBorrowId());
            record.setMemberId(entity.getMemberId());
            record.setBookId(entity.getBookId());
            record.setBorrowDate(entity.getBorrowDate());
            record.setReturnDate(entity.getReturnDate());

            borrowRecordsList.add(record);
        }
        return borrowRecordsList;
    }

    @Override
    public BorrowBooks getBorrowRecordByBookId(String selectedBookId) throws SQLException {
        BorrowBooksEntity borrowRecordByBookId = borrowDao.getBorrowRecordByBookId(selectedBookId);

        if (borrowRecordByBookId == null) {
            return null;
        }

        BorrowBooks record = new BorrowBooks();
        record.setBorrowId(borrowRecordByBookId.getBorrowId());
        record.setMemberId(borrowRecordByBookId.getMemberId());
        record.setBookId(borrowRecordByBookId.getBookId());
        record.setBorrowDate(borrowRecordByBookId.getBorrowDate());
        record.setReturnDate(borrowRecordByBookId.getReturnDate());

        return record;
    }

    @Override
    public boolean returnBookWithFine(String selectedBookId, LocalDate today, double fineAmount) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String borrowId = borrowDao.getBorrowIdByBookId(selectedBookId, connection);

            if (borrowId == null) {
                throw new SQLException("No borrow record found for the selected book!");
            }

            boolean isBorrowUpdated = borrowDao.updateBorrowRecordAsReturned(selectedBookId, today, connection);

            boolean isBookUpdated = bookDao.updateAvailabilityStatus(selectedBookId, "Available", connection);

            boolean isFineSaved = true;
            if (fineAmount > 0) {
                FineEntity fine = new FineEntity();
                fine.setBorrowId(borrowId);
                fine.setFineAmount(BigDecimal.valueOf(fineAmount));
                fine.setPaymentDate(LocalDate.now());
                isFineSaved = borrowDao.saveFine(fine, connection);
            }


            if (isBorrowUpdated && isBookUpdated && isFineSaved) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    private String generateNextBorrowId() throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            String lastBorrowId = borrowDao.getLastBorrowId(connection);

            if (lastBorrowId == null) {
                return "BR0001";
            } else {

                int lastNumber = Integer.parseInt(lastBorrowId.substring(2));
                int nextNumber = lastNumber + 1;


                return String.format("BR%04d", nextNumber);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }
}

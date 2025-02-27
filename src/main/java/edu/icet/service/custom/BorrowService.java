package edu.icet.service.custom;

import edu.icet.dto.Book;
import edu.icet.dto.BorrowBooks;
import edu.icet.dto.Member;
import edu.icet.service.SuperService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BorrowService extends SuperService {
    Member searchMemberById(String memberId) throws SQLException;

    Book searchBookById(String bookId) throws SQLException;

    boolean saveBorrowRecord(String memberId, String bookId, LocalDate borrowDate, LocalDate returnDate) throws SQLException;

    boolean hasUnreturnedBooks(String memberId) throws SQLException;

    List<BorrowBooks> getBorrowRecordsByMemberId(String memberId) throws SQLException;

    BorrowBooks getBorrowRecordByBookId(String selectedBookId) throws SQLException;

    boolean returnBookWithFine(String selectedBookId, LocalDate today, double fineAmount) throws SQLException;
}

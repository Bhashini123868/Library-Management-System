package edu.icet.repository.custom;

import edu.icet.entity.BookEntity;
import edu.icet.entity.BorrowBooksEntity;
import edu.icet.entity.FineEntity;
import edu.icet.entity.MemberEntity;
import edu.icet.repository.CrudDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BorrowDao extends CrudDao<BookEntity,String> {
    BookEntity searchBookById(String bookId) throws SQLException;

    MemberEntity searchById(String memberId) throws SQLException;

    boolean saveTheRecord(BorrowBooksEntity borrowRecord, Connection connection) throws SQLException;

    String getLastBorrowId(Connection connection) throws SQLException;

    boolean hasUnreturnedBooks(String memberId) throws SQLException;

    List<BorrowBooksEntity> getBorrowRecordsByMemberId(String memberId) throws SQLException;

    BorrowBooksEntity getBorrowRecordByBookId(String selectedBookId) throws SQLException;

    boolean updateBorrowRecordAsReturned(String selectedBookId, LocalDate today, Connection connection);

    String getBorrowIdByBookId(String selectedBookId, Connection connection) throws SQLException;

    boolean saveFine(FineEntity fine, Connection connection) throws SQLException;
}

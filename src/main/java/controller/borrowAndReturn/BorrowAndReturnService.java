package controller.borrowAndReturn;

import model.BorrowedBooks;
import java.util.List;

public interface BorrowAndReturnService {
    List<BorrowedBooks> getAll();
    boolean updateBorrowAndReturn(BorrowedBooks borrowAndReturn);
    boolean deleteBorrowAndReturn(String recordID);
    BorrowedBooks searchBorrowAndReturn(String recordID);
    boolean saveBorrowAndReturn(BorrowedBooks borrowAndReturn);
}

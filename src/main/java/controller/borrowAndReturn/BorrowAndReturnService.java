package controller.borrowAndReturn;

import model.BorrowAndReturn;
import java.util.List;

public interface BorrowAndReturnService {
    List<BorrowAndReturn> getAll();
    boolean updateBorrowAndReturn(BorrowAndReturn borrowAndReturn);
    boolean deleteBorrowAndReturn(String recordID);
    BorrowAndReturn searchBorrowAndReturn(String recordID);
    boolean saveBorrowAndReturn(BorrowAndReturn borrowAndReturn);
}

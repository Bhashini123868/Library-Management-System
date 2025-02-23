package services;



import model.ReturnBook;

import java.util.List;

public interface ReturnBook_Service {
    boolean addBooks (ReturnBook returnBook);
    List<ReturnBook> getAll();
}

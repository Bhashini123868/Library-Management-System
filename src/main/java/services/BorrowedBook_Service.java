package services;

import model.BorrowedBooks;

import java.util.List;

public interface BorrowedBook_Service {
    boolean addBooks (BorrowedBooks borrowedBooks);
    List<BorrowedBooks> getAll();
    public BorrowedBooks searchMember(String borrowedBooks);

}

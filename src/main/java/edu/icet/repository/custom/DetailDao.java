package edu.icet.repository.custom;

import edu.icet.dto.Book;
import edu.icet.repository.CrudDao;

public interface DetailDao extends CrudDao<Book,String> {

    Integer getCustomerCount();

    Integer getBookCount();

    Double getFineCount();

    Integer getOverDueBooksCount();

    Integer getUnpaidFineCount();
}

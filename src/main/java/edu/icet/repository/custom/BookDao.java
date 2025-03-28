package edu.icet.repository.custom;

import edu.icet.entity.BookEntity;
import edu.icet.repository.CrudDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookDao extends CrudDao<BookEntity,String> {

    String getLastBookID();

    boolean updateAvailabilityStatus(String bookId, String borrowed, Connection connection) throws SQLException;

}

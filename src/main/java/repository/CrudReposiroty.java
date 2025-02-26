package repository;

import entity.LoginEntity;
import model.Book;
import model.Staff;
import model.Users;

import java.sql.SQLException;
import java.util.List;


public interface CrudReposiroty<T> extends SuperRepository{

    Book add(T entity);
    boolean update(T entity);
    boolean delete(String id);
    Users Search(String entity);

    List<Book> getAll();
    Book SerachBook(String entity);
    List<Users> getAl();
    Users addMemeber(T entity);

    Staff addStaff(Staff entity);
    Staff searchStaff(String entity);
    List<Staff> getAllStaff();

    LoginEntity getUser(String user_name , String password) throws SQLException;

}

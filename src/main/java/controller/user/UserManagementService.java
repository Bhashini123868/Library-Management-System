package controller.user;

import model.Users;

import java.sql.SQLException;
import java.util.List;

public interface UserManagementService {
    List<Users> getAll() throws SQLException;
    boolean updateUser(Users users) throws SQLException;
    boolean deleteUser(String userID) throws SQLException;
    Users searchUser(String userID) throws SQLException;
    boolean saveUser(Users users) throws SQLException;
}

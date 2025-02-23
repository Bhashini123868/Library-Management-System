package services;

import model.Login;

import java.sql.SQLException;
import java.util.List;

public interface Signin_Service {
    List<Login> getAlluser() throws SQLException;
    Boolean addUser(Login login);
}

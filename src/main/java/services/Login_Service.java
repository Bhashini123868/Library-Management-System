package services;

import model.Login;

import java.sql.SQLException;

public interface Login_Service {
    Login getUser(String user_name , String password) throws SQLException;

}

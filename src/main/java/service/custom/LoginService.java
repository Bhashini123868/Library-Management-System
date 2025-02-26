package service.custom;

import entity.LoginEntity;
import service.SuperService;

import java.sql.SQLException;

public interface LoginService extends SuperService {
    LoginEntity getUser(String user_name , String password) throws SQLException;
}

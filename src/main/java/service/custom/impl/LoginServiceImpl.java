package service.custom.impl;

import entity.LoginEntity;
import repository.custom.LoginRepository;
import service.custom.LoginService;

import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    @Override
    public LoginEntity getUser(String user_name, String password) throws SQLException {
        LoginEntity login = LoginRepository.getUser(user_name,password);
    }
}

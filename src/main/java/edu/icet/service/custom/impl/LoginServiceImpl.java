package edu.icet.service.custom.impl;

import edu.icet.entity.UserEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.LoginDao;
import edu.icet.service.custom.LoginService;
import edu.icet.util.DaoType;

import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {

    LoginDao loginDao= DaoFactory.getInstance().getDaoType(DaoType.LOGIN);

    private static LoginServiceImpl instance;

    private LoginServiceImpl(){

    }

    public static LoginServiceImpl getInstance(){
        return instance == null ? instance=new LoginServiceImpl() : instance;
    }

    @Override
    public boolean authenticateUser(String email, String password) throws SQLException {
        UserEntity userEntity = loginDao.authenticateUser(email, password);
        if (userEntity != null) {
            return true;
        }
        return false;
    }

}

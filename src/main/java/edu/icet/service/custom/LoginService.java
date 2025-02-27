package edu.icet.service.custom;

import edu.icet.entity.UserEntity;
import edu.icet.service.SuperService;

import java.sql.SQLException;

public interface LoginService extends SuperService {

    boolean authenticateUser(String email, String password) throws SQLException;

}

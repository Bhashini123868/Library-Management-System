package edu.icet.repository.custom;

import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.repository.CrudDao;

import java.sql.SQLException;

public interface SignupDao extends CrudDao <UserEntity,String> {

    UserEntity checkemailrepeat(String email) throws SQLException;

    boolean registerUser(UserEntity newUser) throws SQLException;

    UserEntity generateUserID() throws SQLException;

}

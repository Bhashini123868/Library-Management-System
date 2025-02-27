package edu.icet.repository.custom;


import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.repository.CrudDao;

import java.sql.SQLException;


public interface LoginDao extends CrudDao<UserEntity,String> {

    UserEntity authenticateUser(String email, String password) throws SQLException;

}

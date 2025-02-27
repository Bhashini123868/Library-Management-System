package edu.icet.repository.custom;

import edu.icet.entity.UserEntity;
import edu.icet.repository.CrudDao;

public interface FogotDao extends CrudDao<UserEntity,String> {

    UserEntity checkemailrepeat(String email);

    UserEntity updatePassword(String email,String newPassword);

}

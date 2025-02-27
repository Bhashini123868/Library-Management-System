package edu.icet.service.custom.impl;

import edu.icet.entity.UserEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.FogotDao;
import edu.icet.service.custom.FogotService;
import edu.icet.util.DaoType;

public class FogotPasswordServiceImpl implements FogotService {
    private static FogotPasswordServiceImpl instance;

    FogotDao fogotDao= DaoFactory.getInstance().getDaoType(DaoType.FOGGOTPASSWORD);

    private FogotPasswordServiceImpl(){}

    public static FogotPasswordServiceImpl getInstance(){
        return instance==null?new FogotPasswordServiceImpl():instance;
    }

    @Override
    public boolean isEmailValid(String email) {
        UserEntity userEntity = fogotDao.checkemailrepeat(email);
        return userEntity != null;
    }


    @Override
    public boolean updatePasswordInDatabase(String email, String newPassword) {
        UserEntity userEntity = fogotDao.updatePassword(email, newPassword);
        return userEntity != null;
    }
}


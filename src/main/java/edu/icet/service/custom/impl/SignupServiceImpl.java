package edu.icet.service.custom.impl;

import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.SignupDao;
import edu.icet.service.custom.SignupService;
import edu.icet.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

public class SignupServiceImpl implements SignupService {

    private final ModelMapper modelMapper=new ModelMapper();

    SignupDao signupDao= DaoFactory.getInstance().getDaoType(DaoType.SINGNUP);

    private static SignupServiceImpl instance;

    private SignupServiceImpl(){

    }

    public static SignupServiceImpl getInstance(){
        return instance==null ? instance=new SignupServiceImpl() : instance;
    }

    @Override
    public boolean checkemailrepeat(String email) throws SQLException {
        UserEntity userEntity = signupDao.checkemailrepeat(email);
        return userEntity == null;
    }
    @Override
    public boolean registerUser(User newUser) throws SQLException {
        UserEntity entity = modelMapper.map(newUser, UserEntity.class);
        return signupDao.registerUser(entity);
    }

    @Override
    public String genarateuserID() throws SQLException {
        UserEntity userEntity = signupDao.generateUserID();
        return userEntity.getId();

    }
}

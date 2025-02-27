package edu.icet.service.custom;

import edu.icet.dto.User;
import edu.icet.service.SuperService;

import java.sql.SQLException;

public interface SignupService extends SuperService {

    boolean checkemailrepeat(String email) throws SQLException;

    boolean registerUser(User newUser) throws SQLException;

    String genarateuserID() throws SQLException;




}

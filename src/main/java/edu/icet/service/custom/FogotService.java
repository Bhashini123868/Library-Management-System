package edu.icet.service.custom;

import edu.icet.service.SuperService;

public interface FogotService extends SuperService {

    boolean isEmailValid(String email);
    boolean updatePasswordInDatabase(String email,String newPassword);

}

package services;

import model.Users;

import java.util.List;

public interface User_Service {
    List<Users> getAll();

    Users searchMember(String members);

    boolean updateMemeber(Users members);

    boolean deleteMemeber(Users member_id);

    void  AddMemeber(Users members);
}

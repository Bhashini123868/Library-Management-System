package service.custom;

import model.Users;
import service.SuperService;

import java.util.List;

public interface UserService extends SuperService {
    List<Users> getAll();
    Users serchUser(String members);
    boolean updateUser(Users members);
    boolean deleteUser(Users member_id);
    boolean  addUser(Users members);
}

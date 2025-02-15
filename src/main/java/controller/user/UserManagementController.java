package controller.user;

import db.Db_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.*;
import java.util.List;
import java.sql.Date;

public class UserManagementController implements UserManagementService{
    private static UserManagementController instance;

    private UserManagementController(){
    }

    public static UserManagementController getInstance(){
        if (instance == null) instance = new UserManagementController();
        return instance;
    }

    @Override
    public List<Users> getAll() throws SQLException {
         Statement statement = Db_Connection.getInstance().getConnection().createStatement();
         ResultSet resultSet = statement.executeQuery("Select * from Users");
        ObservableList<Users> usersObList = FXCollections.observableArrayList();
        while (resultSet.next()){
            usersObList.add(new Users(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return usersObList;
    }

    @Override
    public boolean updateUser(Users users) throws SQLException {
         Connection connection = Db_Connection.getInstance().getConnection();
         PreparedStatement stm = connection.prepareStatement("Update users set userName=?, contact=?, membershipdate=? where userID=? ");
         stm.setObject(1, users.getUserID());
         stm.setObject(2, users.getUserName());
         stm.setObject(3,users.getContact());
         stm.setObject(4,users.getMembershipDate());
        return stm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteUser(String userID) throws SQLException {
         Connection connection = Db_Connection.getInstance().getConnection();
         PreparedStatement stm = connection.prepareStatement("Delete from users where userID=?");
         stm.setString(1,userID);
        return stm.executeUpdate() > 0;
    }

    @Override
    public Users searchUser(String userID) throws SQLException {
         Connection connection = Db_Connection.getInstance().getConnection();
         PreparedStatement stm = connection.prepareStatement("Select * from users where userID=?");
         stm.setString(1, userID);
         ResultSet resultSet = stm.executeQuery();
         if (resultSet.next()){
             Users users = new Users(
                     resultSet.getString(1),
                     resultSet.getString(2),
                     resultSet.getString(3),
                     resultSet.getString(4)
             );
             return users;
         }
        return null;
    }

    @Override
    public boolean saveUser(Users users) throws SQLException {
         Connection connection = Db_Connection.getInstance().getConnection();
         PreparedStatement stm = connection.prepareStatement("INSERT INTO Users VALUES (?,?,?,?)");

        stm.setString(1, users.getUserID());
        stm.setString(2, users.getUserName());
        stm.setString(3, users.getContact());
        stm.setDate(4, Date.valueOf(users.getMembershipDate()));

        return stm.executeUpdate() > 0;
    }
}

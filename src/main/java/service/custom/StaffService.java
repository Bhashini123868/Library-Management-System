package service.custom;

import entity.StaffEntity;
import model.Staff;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface StaffService extends SuperService {
    List<Staff> getAll();
    Staff SearchStaff(String staff) throws SQLException;
    boolean UpdateBooks(StaffEntity staff);
    boolean DeleteBooks(Staff staffid);
    boolean  addstaff (Staff staff);
}

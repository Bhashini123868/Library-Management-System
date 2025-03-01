package edu.icet.repository.custom.impl;

import edu.icet.dbconnection.DBConnection;
import edu.icet.entity.MemberEntity;
import edu.icet.repository.custom.MemberDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao {

    private static final String INSERT_MEMBER_SQL = "INSERT INTO member (MemberId, Name, ContactNumber, MembershipDate) VALUES (?, ?, ?, ?)";
    private static final String SELECT_LAST_MEMBER_ID_SQL = "SELECT MemberId FROM member ORDER BY MemberId DESC LIMIT 1";
    private static final String SELECT_ALL_MEMBERS_SQL = "SELECT * FROM member";
    private static final String DELETE_MEMBER_SQL = "DELETE FROM member WHERE MemberId = ?";
    private static final String SELECT_MEMBER_BY_ID_SQL = "SELECT * FROM member WHERE MemberId = ?";
    private static final String UPDATE_MEMBER_SQL = "UPDATE member SET Name = ?, ContactNumber = ?, MembershipDate = ? WHERE MemberId = ?";


    @Override
    public boolean save(MemberEntity entity) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MEMBER_SQL)) {

            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getContact());
            preparedStatement.setDate(4, Date.valueOf(entity.getDate()));

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }


    @Override
    public boolean update(MemberEntity entity) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MEMBER_SQL)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getContact());
            preparedStatement.setDate(3, Date.valueOf(entity.getDate()));
            preparedStatement.setString(4, entity.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public MemberEntity search(String memberId) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEMBER_BY_ID_SQL)) {

            preparedStatement.setString(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new MemberEntity(
                        resultSet.getString("MemberId"),
                        resultSet.getString("Name"),
                        resultSet.getString("ContactNumber"),
                        resultSet.getDate("MembershipDate").toLocalDate()
                );
            }
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MEMBER_SQL)) {

            preparedStatement.setString(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public List<MemberEntity> getAll() throws SQLException {
        List<MemberEntity> members = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MEMBERS_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                members.add(new MemberEntity(
                        resultSet.getString("MemberId"),
                        resultSet.getString("Name"),
                        resultSet.getString("ContactNumber"),
                        resultSet.getDate("MembershipDate").toLocalDate()
                ));
            }
        }
        return members;

    }

    @Override
    public String getLastMemberID() throws SQLException {
        String lastMemberID = "M0000";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_MEMBER_ID_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lastMemberID = resultSet.getString("MemberId");
            }
        }
        return lastMemberID;
    }

}

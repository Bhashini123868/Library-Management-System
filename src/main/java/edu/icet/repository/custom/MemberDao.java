package edu.icet.repository.custom;

import edu.icet.dto.Member;
import edu.icet.entity.MemberEntity;
import edu.icet.repository.CrudDao;

import java.sql.SQLException;

public interface MemberDao extends CrudDao<MemberEntity,String> {

    String getLastMemberID() throws SQLException;

}

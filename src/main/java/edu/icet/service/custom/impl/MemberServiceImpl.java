package edu.icet.service.custom.impl;

import edu.icet.dto.Member;
import edu.icet.entity.MemberEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.MemberDao;
import edu.icet.service.custom.MemberService;
import edu.icet.util.DaoType;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

    MemberDao memberDao= DaoFactory.getInstance().getDaoType(DaoType.MEMBER);

    private static MemberServiceImpl instance;

    private MemberServiceImpl(){

    }

    public static MemberServiceImpl getInstance(){
        return instance == null ? new MemberServiceImpl():instance;
    }

    @Override
    public boolean addMember(Member member) throws SQLException {

        String lastMemberID = memberDao.getLastMemberID();
        String newMemberID = generateNextMemberID(lastMemberID);

        MemberEntity memberEntity = new MemberEntity(
                newMemberID,
                member.getName(),
                member.getContact(),
                member.getDate()
        );

        boolean isAdded = memberDao.save(memberEntity);
        return isAdded;

    }

    @Override
    public List<Member> getAllMembers() throws SQLException {
        List<MemberEntity> members = memberDao.getAll();
        return members.stream()
                .map(member -> new Member(
                        member.getId(),
                        member.getName(),
                        member.getContact(),
                        member.getDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String showtheId() throws SQLException {
        return generateNextMemberID(memberDao.getLastMemberID());
    }

    @Override
    public Member searchMemberById(String memberId) throws SQLException {
        MemberEntity memberEntity = memberDao.search(memberId);
        if (memberEntity == null) {
            return null;
        }
        return new Member(memberEntity.getId(),memberEntity.getName(),memberEntity.getContact(),memberEntity.getDate());
    }

    @Override
    public boolean deleteMember(String memberId) throws SQLException {
        return memberDao.delete(memberId);
    }

    @Override
    public boolean updateMember(Member member) throws SQLException {
        MemberEntity memberEntity = convertToEntity(member);
        return memberDao.update(memberEntity);
    }

    private String generateNextMemberID(String lastMemberID) {
        int lastNumber = Integer.parseInt(lastMemberID.substring(1));
        int nextNumber = lastNumber + 1;
        return String.format("M%04d", nextNumber);
    }

    private MemberEntity convertToEntity(Member member) {
        return new MemberEntity(
                member.getMemberid(),
                member.getName(),
                member.getContact(),
                member.getDate()
        );
    }
}

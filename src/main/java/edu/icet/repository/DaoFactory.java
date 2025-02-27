package edu.icet.repository;

import edu.icet.repository.custom.impl.*;
import edu.icet.util.DaoType;

public class DaoFactory {

    private static DaoFactory instance;
    private DaoFactory(){}
    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao> T getDaoType(DaoType type){
        switch (type){
            case LOGIN: return (T) new LoginDaoImpl();
            case SINGNUP:return (T) new SignupDaoImpl();
            case FOGGOTPASSWORD:return (T) new FogotDaoImpl();
            case MEMBER:return (T) new MemberDaoImpl();
            case BOOK:return (T) new BookDaoImpl();
            case BORROW:return (T) new BorrowDaoImpl();
            case DETAILSCONTROLLER:return (T) new DetailDaoImpl();
        }
        return null;
    }

}

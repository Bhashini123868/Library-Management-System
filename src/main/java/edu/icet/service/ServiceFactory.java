package edu.icet.service;

import edu.icet.service.custom.impl.*;
import edu.icet.util.ServiceType;

public class ServiceFactory {

    private static ServiceFactory instance;

    private ServiceFactory(){}
    public static ServiceFactory getInstance() {
        return instance==null?instance=new ServiceFactory():instance;
    }
    public <T extends SuperService>  T getServiceType(ServiceType serviceType){
        switch (serviceType){
            case LOGIN:return (T) LoginServiceImpl.getInstance();
            case SIGNUP:return (T) SignupServiceImpl.getInstance();
            case FOGGOTPASSWORD:return (T) FogotPasswordServiceImpl.getInstance();
            case MEMBER:return (T) MemberServiceImpl.getInstance();
            case BOOK:return (T) BookServiceImpl.getInstance();
            case BORROW:return (T) BorrowServiceImpl.getInstance();
            case DETAILSCONTROLLER:return (T) DetailControllerServieImpl.getInstance();

        }
        return null;
    }
}

package service;

import service.custom.impl.BookServiceImpl;
import service.custom.impl.StaffServiceImpl;
import service.custom.impl.UserServiceImpl;
import util.ServiceType;

public class ServiceFactory {
    public static ServiceFactory instance;
    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance == null ? instance = new ServiceFactory() : instance;
    }

    public <T extends SuperService> T getServiceType(ServiceType serviceType){
        switch (serviceType) {
            case AddBooks: return (T) new BookServiceImpl();
            case AddUser: return (T) new UserServiceImpl();
            case AddStaff: return (T) new StaffServiceImpl();
        }
        return null;
    }
}

package repository;

import repository.custom.impl.BookRepositoryImpl;
import repository.custom.impl.StaffRepositoryImpl;
import repository.custom.impl.UserRepositoryImpl;
import util.RepositoryType;

public class RepositoryFactory {
    private static RepositoryFactory instance;
    private RepositoryFactory() {}

    public static RepositoryFactory getInstance() {
        return instance == null ? instance = new RepositoryFactory() : instance;
    }

    public <T extends SuperRepository> T getRepository_Factory(RepositoryType type) {
        switch (type) {
            case Book: return (T) new BookRepositoryImpl();
            case User: return (T) new UserRepositoryImpl();
            case Staff: return (T) new StaffRepositoryImpl();
        }
        return null;
    }
}

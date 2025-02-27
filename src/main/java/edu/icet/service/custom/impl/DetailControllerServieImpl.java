package edu.icet.service.custom.impl;

import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.DetailDao;
import edu.icet.service.custom.DetailControllerService;
import edu.icet.util.DaoType;

public class DetailControllerServieImpl implements DetailControllerService {

    DetailDao detailDao= DaoFactory.getInstance().getDaoType(DaoType.DETAILSCONTROLLER);

    private static DetailControllerServieImpl instance;

    private DetailControllerServieImpl(){};

    public static DetailControllerServieImpl getInstance(){
        return instance==null?new DetailControllerServieImpl():instance;
    }

    @Override
    public Integer getCustomerCount() {
        return detailDao.getCustomerCount();
    }

    @Override
    public Integer getBooksCount() {
        return detailDao.getBookCount();
    }

    @Override
    public Double getFineCount() {
        return detailDao.getFineCount();
    }

    @Override
    public Integer getOverDueBooksCount() {
        return detailDao.getOverDueBooksCount();
    }

    @Override
    public Integer getUnpaidFineCount() {
        return detailDao.getUnpaidFineCount();
    }
}

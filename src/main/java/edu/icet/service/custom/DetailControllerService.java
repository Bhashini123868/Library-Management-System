package edu.icet.service.custom;

import edu.icet.service.SuperService;

public interface DetailControllerService extends SuperService {
    Integer getCustomerCount();

    Integer getBooksCount();

    Double getFineCount();

    Integer getOverDueBooksCount();

    Integer getUnpaidFineCount();
}

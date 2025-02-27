package edu.icet.controller.detailscontroller;

import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.DetailControllerService;
import edu.icet.util.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    public Label lblNowTime;
    public Label lblNowDate;
    public Label overDueBooksCounttxt;
    public Label totalCustomerCounttxt;
    public Label allBookCounttxt;
    public Label totallFineTxt;
    public Label unpaidFineTxt;

    DetailControllerService detailControllerService= ServiceFactory.getInstance().getServiceType(ServiceType.DETAILSCONTROLLER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        getOverDudeBookCount();
        totalCustomerCount();
        allBookCount();
        displayTotalFine();
        unpaidFineCount();
    }

    private void unpaidFineCount() {

        Integer unpaidCount=detailControllerService.getUnpaidFineCount();
        unpaidFineTxt.setText(""+unpaidCount);

    }

    private void displayTotalFine() {

        Double fineConut=detailControllerService.getFineCount();
        totallFineTxt.setText(""+fineConut);

    }

    private void allBookCount() {

        Integer totalBookCount=detailControllerService.getBooksCount();
        allBookCounttxt.setText(""+totalBookCount);

    }

    private void totalCustomerCount() {

        Integer totalCusCount=detailControllerService.getCustomerCount();
        totalCustomerCounttxt.setText(""+totalCusCount);

    }

    private void getOverDudeBookCount() {

        Integer overDueBooksCount=detailControllerService.getOverDueBooksCount();
        overDueBooksCounttxt.setText(""+overDueBooksCount);

    }

    private void loadDateAndTime() {

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblNowDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            lblNowTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }


}

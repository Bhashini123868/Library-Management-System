package controller.report;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportingDashboardController {
    public JFXButton btnListAvailableBooks;
    public JFXButton btnListOverDueFines;
    public JFXButton btnListBorrowedBooks;

    public void btnListAvailableBooksOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ListOfAllAvailableBokksForm.fxml"))));
        stage.show();

    }

    public void btnListOverDueFinesOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ListOfOverdueBooksWithFinesForm.fxml"))));
        stage.show();

    }

    public void btnListBorrowedBooksOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ListOfAllBorrowedBokksForm.fxml"))));
        stage.show();

    }
}

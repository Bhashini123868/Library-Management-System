package controller.report;

import com.jfoenix.controls.JFXButton;
import db.Db_Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;

public class ReportingDashboardController {
    public JFXButton btnListAvailableBooks;
    public JFXButton btnListOverDueFines;
    public JFXButton btnListBorrowedBooks;

    public void btnListAvailableBooksOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/Book_report.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, Db_Connection.getInstance().getConnection());

            JasperExportManager.exportReportToPdfFile(jasperPrint, "customer.pdf");

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }

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

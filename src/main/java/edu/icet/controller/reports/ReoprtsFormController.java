package edu.icet.controller.reports;

import edu.icet.dbconnection.DBConnection;
import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;

public class ReoprtsFormController {

    public void btnAllBooksReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        String reportPath = "src/main/resources/reports/All_Books.jrxml";

        JasperDesign design = JRXmlLoader.load(reportPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);
    }


    public void btnBookAvailabilityOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        String reportPath = "src/main/resources/reports/Books_Availability_report.jrxml";

        JasperDesign design = JRXmlLoader.load(reportPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);
    }

    public void btnAllCustomerReortOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        String reportPath = "src/main/resources/reports/All_Members.jrxml";

        JasperDesign design = JRXmlLoader.load(reportPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);
    }


    public void btnOverdueBooksReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        String reportPath = "src/main/resources/reports/Overdue_books_reports.jrxml";

        JasperDesign design = JRXmlLoader.load(reportPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);
    }
}

//package controller.report;
//
//import com.jfoenix.controls.JFXButton;
//import db.Db_Connection;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.JasperViewer;
//
//import java.sql.SQLException;
//
//public class ListOfAvailableBooksFormController {
//
//    @FXML
//    private JFXButton btnReload;
//
//    @FXML
//    private TableColumn<?, ?> colAuthor;
//
//    @FXML
//    private TableColumn<?, ?> colBookID;
//
//    @FXML
//    private TableColumn<?, ?> colGenre;
//
//    @FXML
//    private TableColumn<?, ?> colISBN;
//
//    @FXML
//    private TableColumn<?, ?> colTitle;
//
//    @FXML
//    private TableView<?> tblAvailablebooks;
//
//    @FXML
//    void btnReloadOnAction(ActionEvent event) {
//        try {
//             JasperDesign design = JRXmlLoader.load("src/main//report/Book_report.jrxml");
//
//             JasperReport jasperReport = JasperCompileManager.compileReport(design);
//
//             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, Db_Connection.getInstance().getConnection());
//
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "customer.pdf");
//
//            JasperViewer.viewReport(jasperPrint, false);
//        } catch (JRException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//}

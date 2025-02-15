package controller.login;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {
    public JFXButton btnBookManagement;
    public JFXButton btnBorrowReturn;
    public JFXButton btnUserManagement;
    public JFXButton btnFineManagement;
    public JFXButton btnReport;

    public AnchorPane loadFormContent;

    public void btnBookManagementOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/bookManagementForm.fxml");

        assert resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.loadFormContent.getChildren().clear();
        this.loadFormContent.getChildren().add(load);

    }

    public void btnBorrowReturnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/borrowAndReturnBooks.fxml");

        assert resource!= null;

        Parent load = FXMLLoader.load(resource);
        this.loadFormContent.getChildren().clear();
        this.loadFormContent.getChildren().add(load);
    }

    public void btnUserManagementOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/userManagementForm.fxml");

        assert resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.loadFormContent.getChildren().clear();
        this.loadFormContent.getChildren().add(load);
    }

    public void btnFineManagementOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/FineManagementForm.fxml");

        assert resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.loadFormContent.getChildren().clear();
        this.loadFormContent.getChildren().add(load);
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/ReportForm.fxml");

        assert resource!=null;

        Parent load = FXMLLoader.load(resource);
        this.loadFormContent.getChildren().clear();
        this.loadFormContent.getChildren().add(load);
    }
}

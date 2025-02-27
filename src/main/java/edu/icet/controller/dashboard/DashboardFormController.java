package edu.icet.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    public AnchorPane dashboardAnchorPaneId;
    public Label logOutTxt;

    public void btnDashBoardOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailsBoard.fxml"));
            AnchorPane pane = loader.load();
            dashboardAnchorPaneId.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public void btnMemberManagmentOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserManagementForm.fxml"));
            AnchorPane pane = loader.load();
            dashboardAnchorPaneId.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnBookManagmentOnAction(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bookManagementForm.fxml"));
            AnchorPane pane = loader.load();
            dashboardAnchorPaneId.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public void btnBorrowOnAction(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BorrowedBooks.fxml"));
            AnchorPane pane = loader.load();
            dashboardAnchorPaneId.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public void btnReturnOnAction(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReturnBooks.fxml"));
            AnchorPane pane = loader.load();
            dashboardAnchorPaneId.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public void btnReportsOnAction(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportForm.fxml"));
            AnchorPane pane = loader.load();
            dashboardAnchorPaneId.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DetailsBoard.fxml"));
            AnchorPane pane = loader.load();
            dashboardAnchorPaneId.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }


    }

    public void logoutTxtOnAction(MouseEvent mouseEvent) throws IOException {
        Stage currentStage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();

        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"))));
        stage.show();
    }

}

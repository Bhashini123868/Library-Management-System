package controller.report;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListOfOverdueWithFineFormController {

    @FXML
    private JFXButton btnReload;

    @FXML
    private TableColumn<?, ?> colBookID;

    @FXML
    private TableColumn<?, ?> colDaysoverdue;

    @FXML
    private TableColumn<?, ?> colFineAmmount;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> collUserName;

    @FXML
    private TableColumn<?, ?> colreturnDate;

    @FXML
    private TableView<?> tblAvailablebooks;

    @FXML
    void btnReloadOnAction(ActionEvent event) {

    }

}

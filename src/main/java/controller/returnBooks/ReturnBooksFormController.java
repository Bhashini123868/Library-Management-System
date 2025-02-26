package controller.returnBooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReturnBooksFormController implements Initializable {

    @FXML
    private AnchorPane Ancor_Return_Book;

    @FXML
    private TableColumn<?, ?> colBookID;

    @FXML
    private TableColumn<?, ?> colBorrowdate;

    @FXML
    private TableColumn<?, ?> colFine;

    @FXML
    private TableColumn<?, ?> colRecordID;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colUserID;

    @FXML
    private ComboBox<?> combo_browid;

    @FXML
    private TextField is_Browwed;

    @FXML
    private Label lable_Date;

    @FXML
    private TextField return_on_time;

    @FXML
    private TableView<?> tblBorrowAndReturn;

    @FXML
    private TextField txt_Amount;

    @FXML
    private TextField txt_Broowed_date;

    @FXML
    private TextField txt_Member_id;

    @FXML
    private TextField txt_Staff_id;

    @FXML
    private TextField txt_bookid;

    @FXML
    private TextField txt_returnid;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void btnFindOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewOnAction(ActionEvent event) {

    }

    @FXML
    void combo_Browedid_Action(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

package controller.borrowAndReturn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.Db_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BorrowAndReturn;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BorrowAndReturnBookFormController implements Initializable {

    @FXML
    private JFXButton btnAdd, btnDelete, btnSearch, btnUpdate;

    @FXML
    private TableColumn<BorrowAndReturn, String> colRecordID, colUserID, colBookID, colBorrowdate, colReturnDate, colFine;

    @FXML
    private TableView<BorrowAndReturn> tblBorrowAndReturn;

    @FXML
    private JFXTextField txtBookID, txtBorrowDate, txtFine, txtRecordID, txtReturnDate, txtUserID;

    private BorrowAndReturnService borrowAndReturnService;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        BorrowAndReturn record = new BorrowAndReturn(
                txtRecordID.getText(),
                txtUserID.getText(),
                txtBookID.getText(),
                txtBorrowDate.getText(),
                txtReturnDate.getText(),
                txtFine.getText()
        );

        boolean isAdded = BorrowAndReturnController.getInstance().saveBorrowAndReturn(record);
        if (isAdded) {
            new Alert(Alert.AlertType.CONFIRMATION, "Record Added Successfully!").show();
            loadTable();
        }

        borrowAndReturnList.add(new BorrowAndReturn(
                txtRecordID.getText(),
                txtUserID.getText(),
                txtBookID.getText(),
                txtBorrowDate.getText(),
                txtReturnDate.getText(),
                txtFine.getText()
        ));

        txtRecordID.clear();
        txtUserID.clear();
        txtBookID.clear();
        txtBorrowDate.clear();
        txtReturnDate.clear();
        txtFine.clear();

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String sql = "DELETE FROM borrow_return WHERE recordID=?";
        try (Connection connection = Db_Connection.getInstance().getConnection();
             PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setString(1, txtRecordID.getText());
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Record Deleted Successfully!").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "No record found to delete.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String sql = "SELECT * FROM borrow_return WHERE recordID=?";
        try (Connection connection = Db_Connection.getInstance().getConnection();
             PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setString(1, txtRecordID.getText());
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                txtUserID.setText(resultSet.getString("userID"));
                txtBookID.setText(resultSet.getString("bookID"));
                txtBorrowDate.setText(resultSet.getString("borrowDate"));
                txtReturnDate.setText(resultSet.getString("returnDate"));
                txtFine.setText(resultSet.getString("fine"));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No Record Found!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String sql = "UPDATE borrow_return SET userID=?, bookID=?, borrowDate=?, returnDate=?, fine=? WHERE recordID=?";
        try (Connection connection = Db_Connection.getInstance().getConnection();
             PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setString(1, txtUserID.getText());
            stm.setString(2, txtBookID.getText());
            stm.setString(3, txtBorrowDate.getText());
            stm.setString(4, txtReturnDate.getText());
            stm.setString(5, txtFine.getText());
            stm.setString(6, txtRecordID.getText());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Record Updated Successfully!").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Record!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    List<BorrowAndReturn> borrowAndReturnList = new ArrayList<>();
    private void loadTable() {
        try {
            ObservableList<BorrowAndReturn> borrowAndReturnListObList = FXCollections.observableArrayList();
            borrowAndReturnListObList.addAll(BorrowAndReturnController.getInstance().getAll());
            tblBorrowAndReturn.setItems(borrowAndReturnListObList);

            colRecordID.setCellValueFactory(new PropertyValueFactory<>("recordID"));
            colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            colBorrowdate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
            colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
            colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading data: " + e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        tblBorrowAndReturn.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            }
        });
    }

    public void setTextToValues(BorrowAndReturn borrowAndReturn) {
        txtRecordID.setText(borrowAndReturn.getRecordID());
        txtUserID.setText(borrowAndReturn.getUserID());
        txtBookID.setText(borrowAndReturn.getBookID());
        txtBorrowDate.setText(borrowAndReturn.getBorrowDate());
        txtReturnDate.setText(borrowAndReturn.getReturnDate());
        txtFine.setText(borrowAndReturn.getFine() + "");
    }
}

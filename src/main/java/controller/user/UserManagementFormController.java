package controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Users;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class UserManagementFormController implements Initializable {

    public JFXTextField txtUserID;
    public JFXButton btnDelete;
    public TableColumn colUserID;
    public TableColumn colContact;
    public TableColumn colMemberDate;

    @FXML
    private TableColumn<Users, String> colUserName;

    @FXML
    private TableView<Users> tblUser;

    @FXML
    private TextField txtContact;

    @FXML
    private Label lblDate;


    @FXML
    private TextField txtUserName;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Users user = new Users(
                    txtUserID.getText(),
                    txtUserName.getText(),
                    txtContact.getText(),
                    lblDate.getText() // Use the date label for membership date
            );

            boolean isAdded = UserManagementController.getInstance().saveUser(user);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "User added successfully!").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add user.").show();
            }

            clearFields();
        } catch (SQLException | NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Error adding user: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            boolean isDeleted = UserManagementController.getInstance().deleteUser(txtUserID.getText());

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "User deleted successfully.").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete user.").show();
            }

            clearFields();
        } catch (SQLException | NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Error deleting user: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        try {
            Users user = UserManagementController.getInstance().searchUser(txtUserID.getText());
            if (user != null) {
                setTextToValues(user);
            } else {
                new Alert(Alert.AlertType.WARNING, "No user found for the given ID.").show();
                clearFields();
            }
        } catch (SQLException | NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Error searching for user: " + e.getMessage()).show();
        }
    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {
        btnSearchOnAction(event);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            Users user = new Users(
                   txtUserID.getText(),
                    txtUserName.getText(),
                    txtContact.getText(),
                    lblDate.getText()
            );

            boolean isUpdated = UserManagementController.getInstance().updateUser(user);

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "User updated successfully.").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user.").show();
            }

            clearFields();
        } catch (SQLException | NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating user: " + e.getMessage()).show();
        }
    }

    private void loadTable() {
        try {
            ObservableList<Users> userObservableList = FXCollections.observableArrayList(
                    UserManagementController.getInstance().getAll()
            );

            tblUser.setItems(userObservableList);

            colUserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
            colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colMemberDate.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading user data: " + e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate();
        loadTable();
        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues(newValue);
            }
        });
    }

    private void setTextToValues(Users user) {
        txtUserID.setText(String.valueOf(user.getUserID()));
        txtUserName.setText(user.getUserName());
        txtContact.setText(user.getContact());
    }

    private void clearFields() {
        txtUserID.clear();
        txtUserName.clear();
        txtContact.clear();
    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(format.format(date));
    }
}
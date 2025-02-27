package edu.icet.controller.Member;

import edu.icet.dbconnection.DBConnection;
import edu.icet.dto.Member;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.MemberService;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MemberManagmentFormController implements Initializable {

    public TextField txtIdField;
    public TextField txtNameField;
    public TextField txtContactField;
    public TableView tblCustomer;
    public DatePicker txtMembershipDateField;
    public TableColumn cusIdCol;
    public TableColumn cusNameCol;
    public TableColumn cusContactCol;
    public TableColumn cusMembershipDateCol;

    MemberService memberService= ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);

    public void addaCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            // Create a MemberDTO object from the form inputs
            String name = txtNameField.getText();
            String contact = txtContactField.getText();
            LocalDate date = txtMembershipDateField.getValue();

            Member member = new Member(null, name, contact, date);

            // Call the service layer to add the member
            boolean isAdded = memberService.addMember(member);
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Member Addded Scusess !").show();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Member Added Failed!").show();
            }

            refreshTable();
            clearForm();
            loadTheId();
        } catch (SQLException e) {
            e.printStackTrace();
            // Show an error message to the user
        }
    }

    private void clearForm() {
        txtNameField.clear();
        txtContactField.clear();
        txtMembershipDateField.setValue(null);
    }

    private void refreshTable() {

        try {
            List<Member> members = memberService.getAllMembers();
            ObservableList<Member> observableList = FXCollections.observableArrayList(members);
            tblCustomer.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            String memberId = txtIdField.getText().trim();

            if (memberId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a member ID to delete.").show();
                return;
            }

            boolean isDeleted = memberService.deleteMember(memberId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Member Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Member Deletion Failed!").show();
            }

            refreshTable();
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the member.").show();
        }
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) {
        try {
            // Get the updated member details from the form fields
            String memberId = txtIdField.getText().trim();
            String name = txtNameField.getText();
            String contact = txtContactField.getText();
            LocalDate date = txtMembershipDateField.getValue();

            if (memberId.isEmpty() || name.isEmpty()||contact.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Search Before to Update !!!!").show();
                return;
            }


            Member member = new Member(memberId, name, contact, date);


            boolean isUpdated = memberService.updateMember(member);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Member Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Member Update Failed!").show();
            }

            refreshTable();
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the member.").show();
        }
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {
        try {
            // Get the member ID from the search field
            String memberId = txtIdField.getText().trim();

            if (memberId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a member ID to search.").show();
                return;
            }

            Member member = memberService.searchMemberById(memberId);

            if (member != null) {
                txtIdField.setText(member.getId());
                txtNameField.setText(member.getName());
                txtContactField.setText(member.getContact());
                txtMembershipDateField.setValue(member.getDate());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No member found with ID: " + memberId).show();
                clearForm();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the member.").show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        cusMembershipDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Load all members into the table
        refreshTable();
        try {
            loadTheId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadTBLOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/bookManagementForm.fxml"));
        stage.setTitle("Member Management");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loadTheId() throws SQLException {
        String s = memberService.showtheId();
        txtIdField.setText(s);
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        String reportPath = "src/main/resources/reports/All_Members.jrxml";

        JasperDesign design = JRXmlLoader.load(reportPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);
    }

}

package edu.icet.controller.ReturnBook;

import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.BorrowBooks;
import edu.icet.dto.Member;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.BorrowService;
import edu.icet.service.custom.MemberService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;


public class ReturnBookFormController implements Initializable {
    public JFXTextField memberIdTxtxField;
    public JFXTextField memberNameTxtField;
    public JFXTextField memberContactTxtField;
    public TableView tblBookReturn;
    public TableColumn bookIdCol;
    public TableColumn borrowDateCol;
    public TableColumn returnDateCol;
    public ComboBox selectBookComboTxt;
    public JFXTextField fineTxtField;

    BorrowService borrowService = ServiceFactory.getInstance().getServiceType(ServiceType.BORROW);
    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);

    public void btnMemberSearchOnAction(ActionEvent actionEvent) {

        String memberId = memberIdTxtxField.getText();

        if (memberId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a Member ID!").show();
            return;
        }

        try {

            Member member = memberService.searchMemberById(memberId);

            if (member == null) {
                new Alert(Alert.AlertType.WARNING, "Member not found!").show();
                memberNameTxtField.clear();
                memberContactTxtField.clear();
                return;
            }

            boolean hasUnreturnedBooks = borrowService.hasUnreturnedBooks(memberId);

            if (!hasUnreturnedBooks) {
                new Alert(Alert.AlertType.WARNING, "This member has no unreturned books!").show();
                memberNameTxtField.clear();
                memberContactTxtField.clear();
                return;
            }

            memberNameTxtField.setText(member.getName());
            memberContactTxtField.setText(member.getContact());

            loadTable(memberId);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    private void loadTable(String memberId) throws SQLException {
        // Fetch borrow records for the member
        List<BorrowBooks> borrowRecords = borrowService.getBorrowRecordsByMemberId(memberId);

        // Set the data in the TableView
        tblBookReturn.getItems().setAll(borrowRecords);

        selectBookComboTxt.getItems().clear(); // Clear existing items
        for (BorrowBooks record : borrowRecords) {
            selectBookComboTxt.getItems().add(record.getBookId()); // Add book IDs
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        borrowDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tblBookReturn.getItems().clear(); // Clear the table initially
    }

    public void btnShowFineOnAction(ActionEvent actionEvent) throws SQLException {
        showfine();
    }

    public void btnPayNowOnAction(ActionEvent actionEvent) {
        if (selectBookComboTxt.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a book to return!").show();
            return;
        }

        String selectedBookId = (String) selectBookComboTxt.getSelectionModel().getSelectedItem();

        try {
            BorrowBooks borrowRecord = borrowService.getBorrowRecordByBookId(selectedBookId);

            if (borrowRecord == null) {
                new Alert(Alert.AlertType.ERROR, "Borrow record not found for the selected book!").show();
                return;
            }

            LocalDate returnDate = borrowRecord.getReturnDate();
            LocalDate today = LocalDate.now();
            double fineAmount = 0;

            if (returnDate.isBefore(today)) {
                long overdueDays = ChronoUnit.DAYS.between(returnDate, today);
                fineAmount = overdueDays * 10;
            }

            boolean isReturned = borrowService.returnBookWithFine(selectedBookId, today, fineAmount);

            if (isReturned) {
                new Alert(Alert.AlertType.INFORMATION, "Book returned successfully!").show();
                fineTxtField.clear();
                loadTable(memberIdTxtxField.getText()); // Reload the table
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to return the book!").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    public void showfine() throws SQLException {
        if (selectBookComboTxt.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a book to return!").show();
            return;
        }

        String selectedBookId = (String) selectBookComboTxt.getSelectionModel().getSelectedItem();

        BorrowBooks borrowRecord = borrowService.getBorrowRecordByBookId(selectedBookId);

        if (borrowRecord == null) {
            new Alert(Alert.AlertType.ERROR, "Borrow record not found for the selected book!").show();
            return;
        }

        LocalDate returnDate = borrowRecord.getReturnDate();
        LocalDate today = LocalDate.now();
        double fineAmount = 0;

        if (returnDate.isBefore(today)) {
            long overdueDays = ChronoUnit.DAYS.between(returnDate, today);
            fineAmount = overdueDays * 10;
            fineTxtField.setText(String.valueOf(fineAmount));
        }
    }

    public void showFineOnAction(ActionEvent actionEvent) throws SQLException {
        showfine();
    }
}

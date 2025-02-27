package edu.icet.controller.borrowBooks;

import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.Book;
import edu.icet.dto.Member;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.BorrowService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BorrowBookFormController implements Initializable {
    public DatePicker returnDatePickerTxtField;
    public JFXTextField memberIdTxtxField;
    public JFXTextField memberNameTxtField;
    public JFXTextField memberContactTxtField;
    public JFXTextField bookIdTxtField;
    public JFXTextField bookNameTxtField;
    public JFXTextField bookAutherTxtField;
    public JFXTextField borrowDateTxtField;

    BorrowService borrowService= ServiceFactory.getInstance().getServiceType(ServiceType.BORROW);


    public void btnBorrowTheBookOnAction(ActionEvent actionEvent) {
        if (memberIdTxtxField.getText().isEmpty() ||
                memberNameTxtField.getText().isEmpty() ||
                memberContactTxtField.getText().isEmpty() ||
                bookIdTxtField.getText().isEmpty() ||
                bookNameTxtField.getText().isEmpty() ||
                bookAutherTxtField.getText().isEmpty() ||
                borrowDateTxtField.getText().isEmpty() ||
                returnDatePickerTxtField.getValue() == null) {

            // Show alert if any field is empty
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
        } else {
            try {
                // Get input values
                String memberId = memberIdTxtxField.getText();
                String bookId = bookIdTxtField.getText();
                LocalDate borrowDate = LocalDate.parse(borrowDateTxtField.getText()); // Parse borrow date
                LocalDate returnDate = returnDatePickerTxtField.getValue(); // Get return date from DatePicker

                // Call the service layer to save the borrow record
                boolean isSaved = borrowService.saveBorrowRecord(memberId, bookId, borrowDate, returnDate);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Book borrowed successfully!").show();
                    clearForm();
                    loadTodayDate();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to borrow the book!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        }
    }



    public void btnMemberSearchOnAction(ActionEvent actionEvent) throws SQLException {
        // Get the member ID from the search field
        String memberId = memberIdTxtxField.getText().trim();

        if (memberId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a member ID to search.").show();
            return;
        }


        Member member = borrowService.searchMemberById(memberId);

        if (member != null) {
            memberIdTxtxField.setText(member.getId());
            memberNameTxtField.setText(member.getName());
            memberContactTxtField.setText(member.getContact());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "No member found with ID: " + memberId).show();
            clearForm();
        }
    }

    public void btnsearchBookOnAction(ActionEvent actionEvent) throws SQLException {

        try {
            String bookId = bookIdTxtField.getText().trim();

            if (bookId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a book ID to search.").show();
                return;
            }

            Book book = borrowService.searchBookById(bookId);

            if (book != null) {
                bookIdTxtField.setText(book.getBookId());
                bookNameTxtField.setText(book.getBookTitle());
                bookAutherTxtField.setText(book.getAuthor());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No book found with ID: " + bookId).show();
                clearForm();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the book.").show();
        }


    }

    private void loadTodayDate(){
        LocalDate today = LocalDate.now();

        // Format date to match JavaFX DatePicker format (yyyy-MM-dd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        borrowDateTxtField.setText(formattedDate);
    }

    private void clearForm() {
        memberIdTxtxField.clear();
        memberContactTxtField.clear();
        memberNameTxtField.clear();
        bookNameTxtField.clear();
        bookAutherTxtField.clear();
        bookIdTxtField.clear();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTodayDate();
        bookAutherTxtField.setEditable(false);
        bookNameTxtField.setEditable(false);
        memberNameTxtField.setEditable(false);
        memberContactTxtField.setEditable(false);
    }

    public void btnsReturnOnAction(ActionEvent actionEvent) throws IOException {

        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ReturnBooks.fxml"))));
        stage.show();

    }
}

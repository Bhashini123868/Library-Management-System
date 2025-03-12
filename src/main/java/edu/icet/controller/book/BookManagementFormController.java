package edu.icet.controller.book;

import edu.icet.dto.Book;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.BookService;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BookManagementFormController implements Initializable {
    public TextField txtIdField;
    public TextField txtIsbnField;
    public TextField txtNameField;
    public TableView tblBook;
    public TableColumn bookIdCol;
    public TableColumn BookIsbnCol;
    public TableColumn bookTitleCol;
    public TableColumn bookAvilabiltyCol;
    public TextField txtAutherField2;
    public TextField txtAvalabelStatuesField;
    public TableColumn bookAutherCol;

    BookService bookService= ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);

    public void loadTBLOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/BorrowedBooks.fxml"))));
        stage.show();
    }
    public void addBookOnAction(ActionEvent actionEvent) {
        try {
            String isbn = txtIsbnField.getText().trim();
            String name = txtNameField.getText().trim();
            String author = txtAutherField2.getText().trim();
            String available = txtAvalabelStatuesField.getText().trim();

            Book book = new Book(null, isbn, name, author, available);

            boolean isAdded = bookService.addBook(book);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Book Added Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Book Addition Failed!").show();
            }
            refreshTable();
            clearForm();
            loadTheId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTheId() {
        String s = bookService.showtheId();
        txtIdField.setText(s);
    }

    private void clearForm() {
        txtNameField.clear();
        txtIsbnField.clear();
        txtAutherField2.clear();
    }

    private void refreshTable() {
        try {
            List<Book> members = bookService.getAllBooks();
            ObservableList<Book> observableList = FXCollections.observableArrayList(members);
            tblBook.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteBookOnAction(ActionEvent actionEvent) {
        try {
            String bookId = txtIdField.getText().trim();
            if (bookId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a book ID to delete!").show();
                return;
            }

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this book?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait();

            if (confirmationAlert.getResult() == ButtonType.YES) {
                boolean isDeleted = bookService.deleteMember(bookId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Book deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Book deletion failed!").show();
                }
                refreshTable();
                clearForm();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the book!").show();
        }
    }
    public void updateBookOnAction(ActionEvent actionEvent) {
        try {
            String bookId = txtIdField.getText().trim();
            String isbn = txtIsbnField.getText();
            String title = txtNameField.getText();
            String author = txtAutherField2.getText();
            String availabilityStatus = txtAvalabelStatuesField.getText();

            if (bookId.isEmpty() || isbn.isEmpty() || title.isEmpty() || author.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Search Before to Update !!!!").show();
                return;
            }

            Book book = new Book(bookId, isbn, title, author, availabilityStatus);
            boolean isUpdated = bookService.updateBook(book);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Book Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Book Update Failed!").show();
            }

            refreshTable();
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the book.").show();
        }
    }
    public void searchBookOnAction(ActionEvent actionEvent) {
        try {
            String bookId = txtIdField.getText().trim();

            if (bookId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a book ID to search.").show();
                return;
            }

            Book book = bookService.searchBookById(bookId);

            if (book != null) {
                txtIdField.setText(book.getBookId());
                txtIsbnField.setText(book.getIsbn());
                txtNameField.setText(book.getBookTitle());
                txtAutherField2.setText(book.getAuthor());
                txtAvalabelStatuesField.setText(book.getAvailability());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No book found with ID: " + bookId).show();
                clearForm();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the book.").show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        BookIsbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookAutherCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookAvilabiltyCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        refreshTable();

        txtAvalabelStatuesField.setEditable(false);
        loadTheId();
    }
}

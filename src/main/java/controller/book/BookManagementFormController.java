package controller.book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Book;
import service.ServiceFactory;
import service.custom.BookService;
import util.ServiceType;

import java.util.List;

public class BookManagementFormController {

    @FXML
    private TextField txtBookID, txtTitle, txtAuthor, txtISBN, txtGenre, txtAvailability;
    @FXML
    private TableView<Book> tblBook;
    @FXML
    private TableColumn<Book, String> colBookID, colTitle, colAuthor, colISBN, colGenre, colAvailability;

    private final BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.AddBooks);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        // Convert txtAvailability to boolean, assuming "true" or "false" are the expected values
        boolean availability = Boolean.parseBoolean(txtAvailability.getText());

        // Create the Book object
        Book book = new Book(txtBookID.getText(), txtTitle.getText(), txtAuthor.getText(),
                txtISBN.getText(), txtGenre.getText(), availability);  // Assuming Book's constructor accepts boolean for availability

        boolean isAdded = bookService.addBook(book);
        if (isAdded) {
            showAlert("Success", "Book added successfully!", Alert.AlertType.INFORMATION);
            refreshTable();
        } else {
            showAlert("Error", "Failed to add book!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String bookID = txtBookID.getText();
        boolean isDeleted = bookService.deleteBook(bookID);
        if (isDeleted) {
            showAlert("Success", "Book deleted successfully!", Alert.AlertType.INFORMATION);
            refreshTable();
        } else {
            showAlert("Error", "Failed to delete book!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        // Convert txtAvailability to boolean, assuming "true" or "false" are the expected values
        boolean availability = Boolean.parseBoolean(txtAvailability.getText());

        // Create the Book object
        Book book = new Book(txtBookID.getText(), txtTitle.getText(), txtAuthor.getText(),
                txtISBN.getText(), txtGenre.getText(), availability);  // Assuming Book's constructor accepts boolean for availability

        boolean isUpdated = bookService.updateBook(book);
        if (isUpdated) {
            showAlert("Success", "Book updated successfully!", Alert.AlertType.INFORMATION);
            refreshTable();
        } else {
            showAlert("Error", "Failed to update book!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String bookID = txtBookID.getText();
        Book book = bookService.searchBook(bookID);
        if (book != null) {
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtISBN.setText(book.getISBN());
            txtGenre.setText(book.getGenre());
            txtAvailability.setText(String.valueOf(book.isAvailable())); // Assuming book.isAvailable() is a boolean
        } else {
            showAlert("Not Found", "No book found with ID: " + bookID, Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void initialize() {
        colBookID.setCellValueFactory(cellData -> cellData.getValue().bookIDProperty());
        colTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        colAuthor.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        colISBN.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        colGenre.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        colAvailability.setCellValueFactory(cellData -> cellData.getValue().availabilityProperty());

        refreshTable();
    }

    private void refreshTable() {
        List<Book> books = bookService.getAllBooks();
        ObservableList<Book> bookList = FXCollections.observableArrayList(books);
        tblBook.setItems(bookList);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

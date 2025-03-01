package edu.icet.controller.book;

import edu.icet.dto.Book;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.BookService;
import edu.icet.util.Category;
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
    public TableColumn bookCategoryCol;
    public TableColumn bookAvilabiltyCol;
    public TextField txtAutherField2;
    public TextField txtAvalabelStatuesField;
    public ComboBox txtCatogoryField;
    public TableColumn bookAutherCol;

    BookService bookService= ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);

    public void loadTBLOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/BorrowedBooks.fxml"))));
        stage.show();
    }

    public void selectCategoryComboOnAction(ActionEvent actionEvent) throws SQLException {


    }

    public void addBookOnAction(ActionEvent actionEvent) {
        try {
            String isbn = txtIsbnField.getText();
            String name = txtNameField.getText();
            String author = txtAutherField2.getText();
            String available = txtAvalabelStatuesField.getText();

            if (txtCatogoryField.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a category!").show();
                return;
            }

            String categoryName = txtCatogoryField.getValue().toString();
            Category selectedCategory = Category.getCategoryByName(categoryName); // Enum එකෙන් category ලබා ගැනීම

            if (selectedCategory == null) {
                new Alert(Alert.AlertType.WARNING, "Invalid category selected!").show();
                return;
            }

            Integer categoryId = selectedCategory.getId();

            Book book = new Book(null, isbn, name, author, categoryId, available);

            boolean isAdded = bookService.addBook(book);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Book Added Successfully!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Book Addition Failed!").show();
            }

            refreshTable();
            clearForm();
            loadTheId();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid category ID format!").show();
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
                new Alert(Alert.AlertType.WARNING, "Please enter a member ID to delete.").show();
                return;
            }

            boolean isDeleted = bookService.deleteMember(bookId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Book Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Book Deletion Failed!").show();
            }

            refreshTable();
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the member.").show();
        }
    }

    public void updateBookOnAction(ActionEvent actionEvent) {

        try {
            String bookId = txtIdField.getText().trim();
            String isbn = txtIsbnField.getText();
            String title = txtNameField.getText();
            String author = txtAutherField2.getText();
            Integer categoryId = Integer.parseInt(txtCatogoryField.getValue().toString());
            String availabilityStatus = txtAvalabelStatuesField.getText();

            if (bookId.isEmpty() || isbn.isEmpty() || title.isEmpty() || author.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Search Before to Update !!!!").show();
                return;
            }

            Book book = new Book(bookId, isbn, title, author, categoryId, availabilityStatus);
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
                txtCatogoryField.setValue(book.getCategoryId());
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
        bookCategoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        bookAvilabiltyCol.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));
        refreshTable();

        txtAvalabelStatuesField.setEditable(false);
        try {
            loadCategoryIds();
            loadTheId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCategoryIds() throws SQLException {
        ObservableList<String> categoryIds = FXCollections.observableArrayList();
        for(String id : bookService.getAllCategoryIds()) {
            categoryIds.add(id);
        }
        txtCatogoryField.setItems(categoryIds);
        System.out.println("ids");
    }
}

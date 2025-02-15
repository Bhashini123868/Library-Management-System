package controller.book;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class BookManagementFormController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colAvailibility;

    @FXML
    private TableColumn<?, ?> colBookID;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colISBN;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableView<Book> tblBook;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtAvailibility;

    @FXML
    private JFXTextField txtBookID;

    @FXML
    private JFXTextField txtGenre;

    @FXML
    private JFXTextField txtISBN;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
        Book book = new Book(
                txtBookID.getText(),
                txtISBN.getText(),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtGenre.getText(),
                Boolean.parseBoolean(txtAvailibility.getText())
        );
        boolean isAdded = BookManagementController.getInstance().saveBook(book);
        if (isAdded){
            new Alert(Alert.AlertType.CONFIRMATION, "Book Added Successfully!").show();
            loadTable();
        }
        bookList.add(new Book(
                txtBookID.getText(),
                txtISBN.getText(),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtGenre.getText(),
                Boolean.parseBoolean(txtAvailibility.getText())
        ));
        txtBookID.clear();
        txtISBN.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtGenre.clear();
        txtAvailibility.clear();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String bookID = txtBookID.getText();
            boolean isDeleted = BookManagementController.getInstance().deleteBook(bookID);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Book Deleted Successfully!").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Book Not Found or Delete Failed!").show();
            }

            txtBookID.clear();
            txtISBN.clear();
            txtTitle.clear();
            txtAuthor.clear();
            txtGenre.clear();
            txtAvailibility.clear();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) {
        try {
            Book book = BookManagementController.getInstance().searchBook(txtBookID.getText());
            if (book !=null){
                txtISBN.setText(book.getISBN());
                txtTitle.setText(book.getTitle());
                txtAuthor.setText(book.getAuthor());
                txtGenre.setText(book.getGenre());
                txtAvailibility.setText(book.getAvailability() +"");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
        Book book = new Book(
                txtBookID.getText(),
                txtISBN.getText(),
                txtTitle.getText(),
                txtAuthor.getText(),
                txtGenre.getText(),
                Boolean.parseBoolean(txtAvailibility.getText())
        );

            boolean isUpdate = BookManagementController.getInstance().updateBook(book);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION, "Book Updated Successfully!").show();
                loadTable();
            }
            txtBookID.clear();
            txtISBN.clear();
            txtTitle.clear();
            txtAuthor.clear();
            txtGenre.clear();
            txtAvailibility.clear();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtISBNOnAction(ActionEvent event) {

    }
    List<Book> bookList = new ArrayList<>();
    private void loadTable () {
        try {
            ObservableList<Book> bookObList = FXCollections.observableArrayList();

            bookObList.addAll(BookManagementController.getInstance().getAll());

            tblBook.setItems(bookObList);

            colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            colISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            colAvailibility.setCellValueFactory(new PropertyValueFactory<>("availability"));
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        tblBook.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTextToValues((Book) newValue);
            }
        });

    }

    public void setTextToValues (Book book){
        txtBookID.setText(book.getBookID());
        txtISBN.setText(book.getISBN());
        txtTitle.setText(book.getTitle() );
        txtAuthor.setText(book.getAuthor());
        txtGenre.setText(book.getGenre());
        txtAvailibility.setText(book.getAvailability() + "");
    }
}

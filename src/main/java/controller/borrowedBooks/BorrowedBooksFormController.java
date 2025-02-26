package controller.borrowedBooks;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.book.BookManagementFormController;
import controller.staff.StaffManagementFormController;
import controller.user.UserManagementFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.Staff;
import model.Users;

import java.net.URL;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class BorrowedBooksFormController implements Initializable {

    public Label lblDate;
    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnTakeBook;

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
    private ComboBox<?> combo_Bookid;

    @FXML
    private ComboBox<?> combo_Staffid;

    @FXML
    private ComboBox<?> combo_UserID;

    @FXML
    private Label lbl_date;

    @FXML
    private TableView<?> tblBorrowAndReturn;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtEmail1;

    @FXML
    private JFXTextField txtISBN;

    @FXML
    private JFXTextField txtLanguage;

    @FXML
    private JFXTextField txtMemberDate;

    @FXML
    private JFXTextField txtMmbrshipDate;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhoneNumber1;

    @FXML
    private JFXTextField txtPhoneNumber2;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtYesNo;

    BookManagementFormController bookManagementFormController = new BookManagementFormController();
    UserManagementFormController userManagementFormController = new UserManagementFormController();
    StaffManagementFormController staffManagementFormController = new StaffManagementFormController();

    @FXML
    void Combo_Book_Action(ActionEvent event) {
        String selectedID = (String) combo_Bookid.getSelectionModel().getSelectedItem();
        System.out.println(selectedID);
        Book book = bookController.getInstancce().SearcBooks(selectedID);
        setComboBookId();
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        txtISBN.setText(book.getISBN());
        txtLanguage.setText(book.getLanguage());

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    ObservableList<Brrowedbooks> brrowedbooksObservableList = FXCollections.observableArrayList();
    @FXML
    void btnTakeBookOnAction(ActionEvent event) {
        brrowedController.addBooks(new Brrowedbooks(
                txt_brrrowedid.getText(),
                (String) combo_Bookid.getValue(),
                (String) combo_Memberid.getValue(),
                (String) combo_Staffid.getValue(),

                lbl_date.getText(),
                txt_yes_or_No.getText()
        ));
        System.out.println("Book successfully added to the database!");


        brrowed_table.setItems(brrowedbooksObservableList);

        colum_borrowedid.setCellValueFactory(new PropertyValueFactory<>("brrowedbokksid"));
        colum_bookid.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colum_memeberid.setCellValueFactory(new PropertyValueFactory<>("memberid"));
        colum_staffid.setCellValueFactory(new PropertyValueFactory<>("staffid"));
        colum_brrow_date.setCellValueFactory(new PropertyValueFactory<>("brooedate"));
        colum_is_broowed.setCellValueFactory(new PropertyValueFactory<>("isBrowwed"));
        LoadDate();

    }

    @FXML
    void combo_Memeberid_Action(ActionEvent event) {
        String selectID = (String) combo_Memberid.getSelectionModel().getSelectedItem();
        System.out.println(selectID);
        //setCombo_Memberid();
        Members members = memberController.searchMember(selectID);
        System.out.println(members.getName());
        txt_full_name.setText(members.getName());
        txt_PhoneNumber.setText(members.getPhoneNumber());
        txt_Address.setText(members.getAdrees());
        txt_Membership.setText(members.getMembershipdates());

    }

    @FXML
    void combo_staffid_Action(ActionEvent event) {
        String selecID = (String) combo_Staffid.getSelectionModel().getSelectedItem();
//        setCombo_Staffid();
        Staff staff = staffController.SearchStaff(selecID);

        String email = staff.getEmail();
        txt_email.setText(email);
        txt_name.setText(staff.getName());
        txt_pohneNumber.setText(staff.getPhoneNumber());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboBookId();
        setComboUserId();
        setComboStaffID();
        LoadDate();
    }

    private void setComboBookId() {
        ObservableList<String> setBookid = FXCollections.observableArrayList();
        for (Book book : bookManagementFormController.getInstancce().getAll()) {
            setBookid.add(book.getBookid());
        }
        combo_Bookid.setItems(setBookid);
    }
    private void setComboUserId(){
        ObservableList<String> setMemeberid = FXCollections.observableArrayList();
        for (Users members : memberController.getInstance().getAll()) {
            setMemeberid.add(members.getMemberid());
            ComboUserId.setItems(setMemeberid);

        }

    }
    private void setComboStaffID(){
        ObservableList<String> setStaffid = FXCollections.observableArrayList();
        for (Staff staff : staffController.getInstance().getAll()) {
            setStaffid.add(staff.getStaffid());
            combo_Staffid.setItems(setStaffid);
        }

    }
    private void LoadDate(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        BreakIterator lblDate;
        lblDate.setText(f.format(date));

    }
}

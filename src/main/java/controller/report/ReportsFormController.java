package controller.report;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportsFormController implements Initializable {

    @FXML
    private CheckBox chbox_books;

    @FXML
    private CheckBox chobox_borrowedbooks;

    @FXML
    private CheckBox chobox_returnbooks;

    @FXML
    private CheckBox chombox_member;

    @FXML
    private DatePicker dt_browwedboks;

    @FXML
    private DatePicker dt_returned_books;

    @FXML
    private TextField txt_bb_nowdate;

    @FXML
    private TextField txt_book_count;

    @FXML
    private TextField txt_member_count;

    @FXML
    private TextField txt_rb_nowdate;

    ReportController reportController=new ReportController();
    @FXML
    void btn_Back_Action(ActionEvent event) {


    }

    @FXML
    void btn_gemerateReporton_Action(ActionEvent event) {
        if (!(isNumber(txt_book_count)) || Integer.parseInt(txt_book_count.getText()) < 0 || txt_book_count.getText().isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Invalid input, please check the fields.").show();
            return;
        }

        if(chbox_books.isSelected()){
            reportController.Generate_Reportby_Count("src/main/resources/Reports/Addbook3.jrxml","booksreport.pdf","SELECT * FROM books LIMIT " + Integer.valueOf(txt_book_count.getText()));
        }

        if(chobox_borrowedbooks.isSelected()){
            reportController.Generate_Reportby_Count("src/main/resources/Reports/broowedbooksReport.jrxml","borrowedbooksreport.pdf","SELECT * FROM borrowedbooks WHERE borrow_date >= '"+dt_browwedboks.getValue().toString()+"' AND borrow_date < '"+txt_bb_nowdate.getText()+"';");
        }

        if(chobox_returnbooks.isSelected()){
            reportController.Generate_Reportby_Count("src/main/resources/Reports/Returnbooks.jrxml","returnedbooksreport.pdf","SELECT * FROM returnsbooks WHERE return_date>= '"+dt_returned_books.getValue().toString()+"' AND return_date < '"+txt_rb_nowdate.getText()+"';");
        }

        if(chombox_member.isSelected()){
            reportController.Generate_Reportby_Count("src/main/resources/Reports/AddmemberReport.jrxml","memberreport.pdf","SELECT * FROM members LIMIT " + Integer.valueOf(txt_member_count.getText()));
        }
    }

    public boolean isNumber(TextField textField) {
        try {
            Double.parseDouble(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadDate();
        configureDatePicker(dt_returned_books);
        configureDatePicker(dt_browwedboks);
    }
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private void configureDatePicker(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });
    }

    private void LoadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        BreakIterator lblDate;
        txt_bb_nowdate.setText(f.format(date));
        txt_rb_nowdate.setText(f.format(date));
    }
}

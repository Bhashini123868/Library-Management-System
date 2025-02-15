package controller.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.Db_Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class signinFormController {

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnSignin;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/libraryHomePage.fxml"))));
        stage.show();

    }

    @FXML
    void btnSigninOnAction(ActionEvent event) throws IOException, SQLException {
        String key = "12345";

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        if (txtPassword.getText().equals(txtPassword.getText())) {
            System.out.println(true);
            Connection connection = Db_Connection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM login WHERE email=" + "'" + txtEmail.getText() + "'");
            if (!resultSet.next()) {
                System.out.println(false);

                String SQL = "INSERT INTO login (username, email, password) VALUES (?,?,?)";
                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setString(1, txtUserName.getText());
                psTm.setString(2, txtEmail.getText());
                psTm.setString(3, basicTextEncryptor.encrypt(txtPassword.getText()));
                psTm.executeUpdate();
            } else {
                System.out.println(true);
            }
        } else {
            System.out.println(false);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"))));
        stage.show();
    }
}
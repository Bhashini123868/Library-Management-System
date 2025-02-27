package edu.icet.controller.singup;

import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.User;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.SignupService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpFromController implements Initializable{

    public Label txtUserID;
    public JFXTextField userNameTxtField;
    public JFXTextField gmailTxtField;
    public JFXTextField passwordTxtField;

    SignupService signUpService = ServiceFactory.getInstance().getServiceType(ServiceType.SIGNUP);

    public void btnSingUpOnAction(ActionEvent actionEvent) throws SQLException, IOException {

        if (userNameTxtField.getText().isEmpty() || gmailTxtField.getText().isEmpty() || passwordTxtField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields!");
            alert.show();
            return;
        }

        if (!gmailTxtField.getText().endsWith("@gmail.com")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Gmail! Use a @gmail.com address.");
            alert.show();
            return;
        }

        if (signUpService.checkemailrepeat(gmailTxtField.getText())) {
            String password = encryptPassword();
            if (signUpService.registerUser(new User(txtUserID.getText(), userNameTxtField.getText(), gmailTxtField.getText(), password))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Registered Successfully");
                alert.showAndWait();

                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Not Registered, Try Again");
                alert.show();
                clear();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Email Already Exists");
            alert.show();
            clear();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getUserID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUserID() throws SQLException {
        String userId = signUpService.genarateuserID();  // Corrected method name here
        txtUserID.setText(userId);
    }

    public String encryptPassword(){
        String key="12345";
        BasicTextEncryptor basicTextEncryptor=new BasicTextEncryptor();
        String password=passwordTxtField.getText();

        basicTextEncryptor.setPassword(key);
        String encriptpassword=basicTextEncryptor.encrypt(password);
        return encriptpassword;
    }


    private void clear(){

        userNameTxtField.clear();
        gmailTxtField.clear();
        passwordTxtField.clear();

    }

    public void btnLoginUpOnAction(ActionEvent actionEvent) throws IOException {

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"))));
        stage.show();
        

    }
}

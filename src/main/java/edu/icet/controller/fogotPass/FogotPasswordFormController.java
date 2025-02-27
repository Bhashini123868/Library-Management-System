package edu.icet.controller.fogotPass;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.FogotService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

public class FogotPasswordFormController {
    public JFXTextField otpCodeTxtField;
    public JFXTextField newPasswordTxtField;
    private String email;
    private String otp;

    FogotService fogotPasswordService= ServiceFactory.getInstance().getServiceType(ServiceType.FOGGOTPASSWORD);

    public void setEmailAndOtp(String email, String otp) {
        this.email = email;
        this.otp = otp;
        System.out.println("Email: " + email);
        System.out.println("OTP: " + otp);      // Check if OTP is set correctly
    }


    public void btnResetOnAction(ActionEvent actionEvent) {
        String enteredOtp = otpCodeTxtField.getText();
        String newPassword = encryptPassword();

        if (enteredOtp.equals(otp)) {
            System.out.println("otp correct");
            if (fogotPasswordService.updatePasswordInDatabase(email, newPassword)) {
                new Alert(Alert.AlertType.INFORMATION, "Password Reset Successfully!").show();
                Stage stage = (Stage) ((JFXButton) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Reset Password. Check The Email And Try Agin 🥰.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid OTP! Please check and enter the correct OTP 🤗 Or Check The Email And Try Agin 🥰.").show();
        }
    }


    public String encryptPassword(){
        String key="12345";
        BasicTextEncryptor basicTextEncryptor=new BasicTextEncryptor();
        String password=newPasswordTxtField.getText();

        basicTextEncryptor.setPassword(key);
        String encriptpassword=basicTextEncryptor.encrypt(password);
        return encriptpassword;
    }

}



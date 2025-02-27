package edu.icet.controller.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.icet.controller.fogotPass.FogotPasswordFormController;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.LoginService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class LoginFormController implements Initializable {
    public JFXTextField emailTxtField;
    public JFXPasswordField passwordTxtField;
    public ImageView imageView;

    private final List<Image> images = new ArrayList<>();
    private int currentIndex = 0;


    LoginService loginService= ServiceFactory.getInstance().getServiceType(ServiceType.LOGIN);

    public void btnLoginOnAction(ActionEvent actionEvent) throws SQLException, IOException {

        if(emailTxtField.getText().isEmpty() || passwordTxtField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields!");
            alert.show();
            return;
        }

        if(loginService.authenticateUser(emailTxtField.getText(), passwordTxtField.getText())){



            try {
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/view/dashBoardForm.fxml"));
                stage.setTitle("Member Management");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Create Account and Try Again");
            alert.show();
        }
    }


    public void btnSignUpOnAction(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/signinForm.fxml"))));
        stage.show();
    }

    public void fogotPasswordBtnOnAction(MouseEvent actionEvent) throws MessagingException, IOException {

        String email = emailTxtField.getText().trim(); // Trim to remove spaces

        if (email.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Fill The Email Field!").show();
            return;
        }

        sendMail(email);
    }


    private void sendMail(String email) throws MessagingException, IOException {
        Properties properties=new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myEmail="";
        String myPassword="";

        Session session= Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,myPassword);
            }
        });
        String otp = otpcodee();
        Message message = makemessage(session, myEmail, email, otp);
        if (message != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Email Sent Successfully!");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fogotpassword.fxml"));
            Parent root = loader.load();

            FogotPasswordFormController controller = loader.getController();
            controller.setEmailAndOtp(email, otp);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

        else{
            new Alert(Alert.AlertType.ERROR,"Sending Fail").show();
        }
        Transport.send(message);
    }

    private Message makemessage(Session session,String myEmail,String email,String otp) throws MessagingException {
        Message message=new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmail));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));

        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: "+otp);

        return message;
    }



    static String otpcodee() {
        Random random=new Random();
        int otp=100000+random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load images (Set the correct paths)

    }



}

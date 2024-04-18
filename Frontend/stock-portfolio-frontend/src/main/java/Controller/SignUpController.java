package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField panNumber;

    @FXML
    private TextField phoneNumber;

    @FXML
    private void handleSignUpAsUser(ActionEvent event) {
        String username_text = username.getText();
        String password_text = password.getText();
        String email_text = email.getText();
        String panNumber_text = panNumber.getText();
        String phoneNumber_text = phoneNumber.getText();
        System.out.println(username_text + " " + password_text + " " + email_text);
    }

    @FXML
    private void handleSignUpAsSEBIOfficial(ActionEvent event) {
        String username_text = username.getText();
        String password_text = password.getText();
        String email_text = email.getText();
        String panNumber_text = panNumber.getText();
        String phoneNumber_text = phoneNumber.getText();
        System.out.println(username_text + " " + password_text + " " + email_text);
    }

    @FXML
    private void handleSignInInstead(ActionEvent event) {
        System.out.println("Sign In Instead");
    }
}

package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import Service.RequestsService;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    private Stage primaryStage;

    private PageController pageController;

    @FXML
    private void handleSignUpAsUser(ActionEvent event) {
        String username_text = username.getText();
        String password_text = password.getText();
        String email_text = email.getText();
        String panNumber_text = panNumber.getText();
        String phoneNumber_text = phoneNumber.getText();
        if (username_text == "" || password_text == "" || email_text == "" || panNumber_text == ""
                || phoneNumber_text == "") {
            return;
        }
        // System.out.println(username_text + " " + password_text + " " + email_text);
        Map<String, Object> data = new HashMap<>();
        data.put("username", username_text);
        data.put("password", password_text);
        data.put("email", email_text);
        data.put("panNumber", panNumber_text);
        data.put("phoneNumber", phoneNumber_text);
        data.put("role", "ROLE_USER");

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Serialize map to JSON string
        try {
            String jsonString = objectMapper.writeValueAsString(data);
            String jsonResponse = RequestsService.postRequest("http://localhost:8081/api/v1/users/signup", jsonString);

            HashMap<String, Object> map = objectMapper.readValue(jsonResponse, HashMap.class);
            pageController.setJwtToken((String) map.get("token"));
            pageController.setUsername(username_text);
            pageController.setEmail(email_text);
            pageController.navigateToPortfolioPage();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        pageController.navigateToSignInPage();
    }
}

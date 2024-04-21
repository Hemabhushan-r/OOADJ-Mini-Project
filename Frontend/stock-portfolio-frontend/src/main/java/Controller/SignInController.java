package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.RequestsService;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInController {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    private Stage primaryStage;

    private PageController pageController;

    @FXML
    private void handleSignInAsUser(ActionEvent event) {
        String password_text = password.getText();
        String email_text = email.getText();
        if (password_text == "" || email_text == "") {
            return;
        }
        // System.out.println(username_text + " " + password_text + " " + email_text);
        Map<String, Object> data = new HashMap<>();
        data.put("password", password_text);
        data.put("email", email_text);

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Serialize map to JSON string
        try {
            String jsonString = objectMapper.writeValueAsString(data);
            String jsonResponse = RequestsService.postRequest("http://localhost:8081/api/v1/users/signin", jsonString);

            HashMap<String, Object> map = objectMapper.readValue(jsonResponse, HashMap.class);
            pageController.setJwtToken((String) map.get("token"));
            pageController.setUsername("");
            pageController.setEmail(email_text);
            pageController.navigateToPortfolioPage();
            // System.out.println(pageController.getJwtToken());
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignInAsSEBIOfficial(ActionEvent event) {
        String password_text = password.getText();
        String email_text = email.getText();
        System.out.println(password_text + " " + email_text);
    }

    @FXML
    private void handleSignUpInstead(ActionEvent event) {
        pageController.navigateToSignUpPage();
    }
}

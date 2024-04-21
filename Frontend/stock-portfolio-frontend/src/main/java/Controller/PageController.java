package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageController {
    private Stage primaryStage;

    private String jwtToken;

    private String username;

    private String email;

    public PageController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void navigateToSignUpPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/signup.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            SignUpController signUpController = fxmlLoader.getController();
            signUpController.setPrimaryStage(primaryStage);
            signUpController.setPageController(this);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sign Up");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public void navigateToSignInPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/signin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            SignInController signInController = fxmlLoader.getController();
            signInController.setPrimaryStage(primaryStage);
            signInController.setPageController(this);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sign In");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public void navigateToPortfolioPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/portfolio.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            PortfolioController portfolioController = fxmlLoader.getController();
            portfolioController.setPrimaryStage(primaryStage);
            portfolioController.setPageController(this);
            portfolioController.getUsernameField().setText(email);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Portfolio App");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public boolean isAuthenticated() {
        // Implement your authentication logic here
        // For demonstration purposes, return true if authenticated, false otherwise
        return false;
    }

    public void initialize() {
        if (isAuthenticated()) {
            navigateToPortfolioPage();
        } else {
            navigateToSignUpPage();
        }
    }
}

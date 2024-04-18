package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageController {
    private Stage primaryStage;

    public PageController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void navigateToSignUpPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/signup.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sign Up");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public void navigateToPortfolioPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("portfolio.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Portfolio");
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

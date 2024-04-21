import Controller.PageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StockPortfolioFrontend extends Application {

    @Override
    public void start(Stage stage) {
        // String javaVersion = System.getProperty("java.version");
        // String javafxVersion = System.getProperty("javafx.version");
        // Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " +
        // javaVersion + ".");
        // Scene scene = new Scene(new StackPane(l), 640, 480);
        // stage.setScene(scene);
        // stage.show();
        PageController pageController = PageController.getPageControllerInstance(stage);
        pageController.initialize();
    }

    public static void main(String[] args) {
        launch();
    }

}
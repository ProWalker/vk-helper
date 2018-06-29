import Controller.MainController;
import Model.ModelDestinationAccounts;
import Model.ModelSourceAccounts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*MainController mainController = new MainController();
        mainController.setStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("View/main_window.fxml"));*/
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(getClass().getResource("View/main_window.fxml").toString()));
        Parent root = loader.load();
        root.getStylesheets().add("Style/main.css");
        MainController mainController = loader.getController();
        mainController.setStage(primaryStage);
        mainController.addModel(new ModelSourceAccounts());
        mainController.addModel(new ModelDestinationAccounts());
        primaryStage.setTitle("Vk Assistant RUK");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

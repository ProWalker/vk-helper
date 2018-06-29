package Controller;

import javafx.stage.Stage;

public class AbstractController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}

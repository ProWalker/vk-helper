package Controller;

import Model.AbstractModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

public class MainController extends AbstractController implements PropertyChangeListener {
    @FXML
    private Label errorLabel;
    @FXML
    private ListView sourceAccs;
    @FXML
    private Button uploadSourceAccs;
    @FXML
    private ListView destinationAccs;
    @FXML
    private Button uploadDestinationAccs;
    @FXML
    private TextArea messageArea;
    @FXML
    private Button uploadImage;
    @FXML
    private Label log;
    @FXML
    private Button goButton;

    private ArrayList<AbstractModel> registeredModels;

    public MainController() {
        registeredModels = new ArrayList<>();
        //errorLabel.setVisible(false);
    }

    @FXML
    private void initialize() {

    }

    @FXML
    public void uploadAccountsHandler(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File sourceAccountsFile = fileChooser.showOpenDialog(super.getStage());
        for (int i = 0; i < registeredModels.size(); i++) {
            registeredModels.get(i).uploadAccounts(sourceAccountsFile);
        }
    }

    public void addModel(AbstractModel abstractModel) {
        registeredModels.add(abstractModel);
        abstractModel.addPropertyChangeListener(this);
    }

    public void removeModel(AbstractModel abstractModel) {
        registeredModels.remove(abstractModel);
        abstractModel.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String currentPropertyName = evt.getPropertyName();
        if (currentPropertyName.equals(AbstractModel.INVALID_FORMAT_ACCOUNT)) {
            errorLabel.setText((String) evt.getNewValue());
            //errorLabel.setVisible(true);
        } else if (currentPropertyName.equals(AbstractModel.LIST_ACCOUNTS_CHANGE)) {
            ArrayList<String> accountsList = (ArrayList<String>) evt.getNewValue();
            sourceAccs.setItems(FXCollections.observableList(accountsList));
        }
    }
}

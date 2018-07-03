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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private void setModelProperty(String methodName, Object newValue) {
        for (int i = 0; i < registeredModels.size(); i++) {
            try {
                Method method = registeredModels.get(i).getClass().getMethod(methodName, newValue.getClass());
                method.invoke(registeredModels.get(i), newValue);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                //e.printStackTrace();
            }
        }
    }

    @FXML
    public void uploadSourceAccountsHandler(ActionEvent actionEvent) {
        File sourceAccountFile = getFileChooser().showOpenDialog(super.getStage());
        setModelProperty("uploadSourceAccounts", sourceAccountFile);
    }

    @FXML
    public void uploadDestinationAccountsHandler(ActionEvent actionEvent) {
        File destinationAccountFile = getFileChooser().showOpenDialog(super.getStage());
        setModelProperty("uploadDestinationAccounts", destinationAccountFile);
    }

    public void addModel(AbstractModel abstractModel) {
        registeredModels.add(abstractModel);
        abstractModel.addPropertyChangeListener(this);
    }

    public void removeModel(AbstractModel abstractModel) {
        registeredModels.remove(abstractModel);
        abstractModel.removePropertyChangeListener(this);
    }

    public FileChooser getFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        return fileChooser;
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

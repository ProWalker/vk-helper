package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;

public abstract class AbstractModel {

    public static String INVALID_FORMAT_ACCOUNT = "INVALID_FORMAT_ACCOUNT";
    public static String LIST_ACCOUNTS_CHANGE = "LIST_ACCOUNTS_CHANGE";

    private String invalidFormatAccountText;

    // Список аккаунтов в виде Списка, для удобного отображения во View
    private ArrayList<String> accountsList = new ArrayList<>();

    private PropertyChangeSupport propertyChangeSupport;

    AbstractModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    ArrayList<String> getAccountsList() {
        return accountsList;
    }

    boolean isAccountValid(String account) {
        return false;
    }

    void addSourceAccount(String account) throws IllegalArgumentException {

    }

    // Метод ответственный за загрузку аккаунтов из файла. Читает строку с аккаунтом и передаёт в метод addSourceAccount
    public void uploadAccounts(File accountsFile) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(accountsFile)));
            String line;
            while ((line = reader.readLine()) != null) {
                addSourceAccount(line);
            }
        } catch (IllegalArgumentException e) { // Если формат аккаунта не верный отсылаем уведомление в контроллер
            firePropertyChange(INVALID_FORMAT_ACCOUNT, "", invalidFormatAccountText);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Если все прошло хорошо, отсылаем уведомление в контроллер и прикрепляем список аккаунтов
        firePropertyChange(LIST_ACCOUNTS_CHANGE, "", accountsList);
    }

    void setInvalidFormatAccountText(String invalidFormatAccountText) {
        this.invalidFormatAccountText = invalidFormatAccountText;
    }

}
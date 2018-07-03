package Model;

import Controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelSourceAccounts extends AbstractModel {

    private String invalidFormatAccountText = "Неверный формат аккаунта. Аккаунт должен быть в формате \"логин:пароль\".";

    // Список аккаунтов в виде Словаря, для удобного использования в программе
    private HashMap<String, String> accountsMap = new HashMap<>();

    public ModelSourceAccounts() {
        super.setInvalidFormatAccountText(invalidFormatAccountText);
    }

    boolean isAccountValid(String account) {
        String[] tokens = account.split(":"); // Разбиваем аккаунт на две части
        if (tokens.length != 2) { // Если после разбивки получилось не 2 части, значит либо что-то не так с форматом аккаунта, либо строка не является аккаунтом
            return false;
        } else if (!(tokens[0].matches("^\\+?\\d*") && (tokens[0].length() == 11 || tokens[0].length() == 12))) { // Проверяем, что первая часть является номером телефона.
            return false;
        } else if (tokens[1].length() == 0) { // Если пароль пустой, возвращаем false
            return false;
        }
        return true;
    }

    // Метод добавления аккаунтов в Map. Перед добавлением передаёт аккаунт на проверку в метод isAccountValid
    void addSourceAccount(String account) throws IllegalArgumentException {
        if (isAccountValid(account)) {
            String[] tokens = account.split(":");
            accountsMap.put(tokens[0], tokens[1]);
            super.getAccountsList().add(account);
        } else {
            throw new IllegalArgumentException("Неверный формат аккаунта!");
        }
    }

    public HashMap<String, String> getSourceAccountsMap() {
        return accountsMap;
    }

    public void uploadSourceAccounts(File accountsFile) {
        super.uploadAccounts(accountsFile);
    }
}

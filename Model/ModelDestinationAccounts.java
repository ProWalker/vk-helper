package Model;

import java.io.File;

public class ModelDestinationAccounts extends AbstractModel {

    public ModelDestinationAccounts() {
        super.setInvalidFormatAccountText("Неверный формат аккаунта. Аккаунт должен быть в виде ссылки на пользователя.");
    }

    // Проверка на валидность аккаунтов destination
    @Override
    boolean isAccountValid(String account) {
        // Формат аккаунта должен быть вида: "https://vk.com/id{цифры}", либо "vk.com/id{цифры}".
        // А так же: https://vk.com/{никнэйм}, либо vk.com/{никнэйм}
        return account.matches("(^https://|^)(vk\\.com/id.*|vk\\.com/.*)");
    }

    @Override
    void addSourceAccount(String account) throws IllegalArgumentException {
        if (isAccountValid(account)) {
            super.getAccountsList().add(account);
        } else {
            throw new IllegalArgumentException("Неверный формат аккаунта!");
        }
    }

    public void uploadDestinationAccounts(File accountsFile) {
        super.uploadAccounts(accountsFile);
    }
}

package RecourcesTests;

import Pages.LoginPage;
import Pages.MonitoringPage;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static DataTests.DataLogin.webLoginAdmin;
import static DataTests.DataLogin.webPassword;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeforeEachTests implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        if (LoginPage.isLoginForm()) LoginPage.loginOnServer(webLoginAdmin, webPassword);
        else if (MonitoringPage.isCheckLogin() && ! MonitoringPage.isCheckUser(webLoginAdmin)) {
            MonitoringPage.clickButtonLogout();
            LoginPage.loginOnServer(webLoginAdmin, webPassword);
        }
        assertTrue(MonitoringPage.isCheckLogin(), "Не удалось авторизоваться на сервере");
    }
}

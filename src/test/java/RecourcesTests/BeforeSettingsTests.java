package RecourcesTests;

import Pages.LoginPage;
import Pages.MonitoringPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.MalformedURLException;
import java.net.URI;


import static DataTests.LOGIN.*;
import static Pages.MonitoringPage.isCheckNotVisibleDownload;
import static Pages.MonitoringPage.isCheckNotVisibleElement;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BeforeSettingsTests {

    public static void StartTests(){

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setVersion("70.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("acceptInsecureCerts", true);

        WebDriver driver = null;
        try {
            driver = WebDriverPool.DEFAULT.getDriver(URI.create(HOST_HUB).toURL(), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WebDriverRunner.setWebDriver(driver);

        Configuration.baseUrl = HOST_SERVER;
        Configuration.startMaximized = true;
        Configuration.screenshots = false;

        if( ! WebDriverRunner.getWebDriver().getCurrentUrl().contains(HOST_SERVER)) open("/");

        if (LoginPage.isLoginForm().isDisplayed()) LoginPage.loginOnServer(LOGIN_ADMIN_WEB, LOGIN_PASSWORD_WEB);
        else if (MonitoringPage.isCheckLogin() && ! MonitoringPage.isCheckUser(LOGIN_ADMIN_WEB)) {
            MonitoringPage.clickButtonLogout();
            LoginPage.loginOnServer(LOGIN_ADMIN_WEB, LOGIN_PASSWORD_WEB);
        }
        assertTrue(MonitoringPage.isCheckLogin(), "Не удалось авторизоваться на сервере");

        if( ! isCheckNotVisibleDownload()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
        if( ! isCheckNotVisibleElement()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));

    }
}

package RecourcesTests;

import Pages.LoginPage;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.ProvidersPage;
import Pages.SubscribersPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.MalformedURLException;
import java.net.URI;

import static DataTests.LOGIN.*;
import static DataTests.SUBSCRIBERS.SUBSCRIBERS_ITEM_MENU;
import static DataTests.Providers.PROVIDER_DX500.DX500_TYPE_PROVIDER;
import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static Pages.MonitoringPage.isCheckNotVisibleDownload;
import static Pages.MonitoringPage.isCheckNotVisibleElement;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BeforeEachTests implements BeforeEachCallback {

    private static DX500Page dx500Page;
    private SubscribersPage subscribersPage;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {


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
        Configuration.screenshots = true;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));

        if( ! WebDriverRunner.getWebDriver().getCurrentUrl().contains(HOST_SERVER)) open("/");

        if (LoginPage.isLoginForm().isDisplayed()) LoginPage.loginOnServer(LOGIN_ADMIN_WEB, LOGIN_PASSWORD_WEB);
        else if (MonitoringPage.isCheckLogin() && ! MonitoringPage.isCheckUser(LOGIN_ADMIN_WEB)) {
            MonitoringPage.clickButtonLogout();
            LoginPage.loginOnServer(LOGIN_ADMIN_WEB, LOGIN_PASSWORD_WEB);
        }
        assertTrue(MonitoringPage.isCheckLogin(), "Не удалось авторизоваться на сервере");
        if( ! isCheckNotVisibleDownload()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
        if( ! isCheckNotVisibleElement()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));

        /*if(String.valueOf(extensionContext.getTestClass()).contains("Test_A_DX500")){
            if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, DX500_TYPE_PROVIDER);
            if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        }

        if(String.valueOf(extensionContext.getTestClass()).contains("Test_C_StatusServers")){
            if( ! MonitoringPage.isSectionMonitoring()) assertTrue(MonitoringPage.clickButtonMonitoringPage());
        }

        if(String.valueOf(extensionContext.getTestClass()).contains("Test_D_AddSubscribers")){
            if (!SubscribersPage.isSubscribersPage().isDisplayed())
                subscribersPage = (SubscribersPage) MonitoringPage.openSectionWEB(SUBSCRIBERS_ITEM_MENU);
            if (subscribersPage == null) subscribersPage = SubscribersPage.getInstance();
        }*/
    }
}

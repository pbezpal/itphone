package RecourcesTests;

import DataTests.DataLogin;
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

import static DataTests.DataLogin.*;
import static DataTests.DataLogin.urlServer;
import static DataTests.DataSubscribers.linkSubscribers;
import static DataTests.Providers.DataProviderDX500.DX500;
import static DataTests.Providers.Providers.linkProvidersPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeforeEachTests implements BeforeEachCallback {

    private static String urlHub = DataLogin.urlHUB;
    private static String portHub = DataLogin.portHub;
    private static DX500Page dx500Page;
    private SubscribersPage subscribersPage;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setVersion("70.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("acceptInsecureCerts", true);

        WebDriver driver = null;
        try {
            driver = WebDriverPool.DEFAULT.getDriver(URI.create(urlHub + portHub + "/wd/hub").toURL(), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WebDriverRunner.setWebDriver(driver);

        Configuration.baseUrl = "https://" + urlServer + ":40443";
        Configuration.startMaximized = true;
        Configuration.screenshots = true;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));


        if( ! WebDriverRunner.getWebDriver().getCurrentUrl().contains("https://" + urlServer + ":40443")) open("/");

        if (LoginPage.isLoginForm().isDisplayed()) LoginPage.loginOnServer(webLoginAdmin, webPassword);
        else if (MonitoringPage.isCheckLogin() && ! MonitoringPage.isCheckUser(webLoginAdmin)) {
            MonitoringPage.clickButtonLogout();
            LoginPage.loginOnServer(webLoginAdmin, webPassword);
        }
        assertTrue(MonitoringPage.isCheckLogin(), "Не удалось авторизоваться на сервере");

        if(String.valueOf(extensionContext.getTestClass()).contains("Test_1_DX500")){
            if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
            if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        }

        if(String.valueOf(extensionContext.getTestClass()).contains("Test_3_StatusServers")){
            if( ! MonitoringPage.isSectionMonitoring()) MonitoringPage.clickButtonMonitoringPage();
        }

        if(String.valueOf(extensionContext.getTestClass()).contains("Test_4_AddSubscribers")){
            if (!SubscribersPage.isSubscribersPage().isDisplayed())
                subscribersPage = (SubscribersPage) MonitoringPage.openSectionWEB(linkSubscribers);
            if (subscribersPage == null) subscribersPage = SubscribersPage.getInstance();
        }
    }


}

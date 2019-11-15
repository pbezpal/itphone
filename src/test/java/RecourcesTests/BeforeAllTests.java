package RecourcesTests;

import DataTests.DataLogin;
import Pages.LoginPage;
import Pages.MonitoringPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.MalformedURLException;
import java.net.URI;


import static DataTests.DataLogin.*;
import static Pages.MonitoringPage.monitoringPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeforeAllTests implements BeforeAllCallback {

    private String urlHub = DataLogin.urlHUB;
    private String portHub = DataLogin.portHub;
    private LoginPage loginPage = null;

    @Override
    public void beforeAll(ExtensionContext context){

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
        Configuration.screenshots = false;

        if( ! WebDriverRunner.getWebDriver().getCurrentUrl().contains("https://" + urlServer + ":40443")) open("/");

    }
}

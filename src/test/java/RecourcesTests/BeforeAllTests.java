package RecourcesTests;

import DataTests.DataLogin;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.UnknownHostException;

import static DataTests.DataLogin.urlServer;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeforeAllTests implements BeforeAllCallback {

    private String urlHub = DataLogin.urlHUB;
    private String portHub = DataLogin.portHub;

    @Override
    public void beforeAll(ExtensionContext context){

        assertTrue(isAvailableWebServer(), "Web сервер недоступен!!!");

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

        //Configuration.browser = "firefox";
        Configuration.screenshots = false;
        Configuration.startMaximized = true;

        open("https://" + urlServer + ":40443");
    }

    @Step(value = "Проверяем, пингуется ли сервер")
    public boolean isAvailableWebServer(){

        InetAddress address = null;
        try{
            address = InetAddress.getByName(urlServer);
        }catch (UnknownHostException host){
            host.printStackTrace();
        }
        boolean availableServer = false;
        try{
            availableServer = address.isReachable(10000);
        }catch (Exception e){}

        return availableServer;
    }
}

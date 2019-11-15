package Test_2_Providers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProvidersTests;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.ProvidersPage;
import RecourcesTests.BeforeEachTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.DataProviderDX500.*;
import static DataTests.Providers.Providers.linkProvidersPage;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@EpicServicesTests
@FeatureProvidersTests
@ExtendWith(TestRules.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_2_DX500 {

    private DX500Page dx500Page;
    private boolean test;
    private boolean screenshot;
    private String failedMessage;

    @BeforeEach
    void setUp(){
        BeforeEachTests.beforeStartTests();
        if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        test = true;
        screenshot = false;
        failedMessage = "";
    }

    @Story(value = "Добавление провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно добавить провайдер DX500")
    @Order(1)
    @Test
    void test_Add_Provider_DX500() {
        if( ! dx500Page.isCheckProvider(DX500)) assertTrue(dx500Page.addDX500Provider(), "Провайдер DX500 не был добавлен");
        if( ! dx500Page.isMySqlDialplan()) failedTestWithScreenshot("Маршрут для DX500 не был добавлен в БД MySql", false);
    }

    @Story(value = "Запуска сервера SIP провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер SIP провайдера DX500")
    @Order(2)
    @Test
    void test__DX500_Start_Server_SIP() {
        if(dx500Page.isConfigurationServerSIP()) {
            dx500Page.clickSectionDX500();
            boolean webStatusSIP = dx500Page.startServer(serverSIP);
            boolean serverStatusSIP = dx500Page.isCheckStartServers(serverSIP + "-dx");
            if(! webStatusSIP && serverStatusSIP) failedTestWithScreenshot("На сервере " + serverSIP + "-dx запущен. В WEB некорректный статус сервера", true);
            else if(! webStatusSIP && serverStatusSIP) failedTestWithScreenshot("На сервере " + serverSIP + "-dx не запущен. В WEB некорректно статус сервера", true);
            else if(! webStatusSIP && ! serverStatusSIP) failedTestWithScreenshot("Cервер " + serverSIP + "-dx не стартовал.", true);
        } else failedTestWithScreenshot("Некорректная конфигурация сервера SIP", false);
    }


    @Story(value = "Запуска сервера Ассистентов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер Ассистентов провайдера DX500")
    @Order(3)
    @Test
    void test_DX500_Start_Server_Booster() {
        if(dx500Page.isConfigurationServerBooster()) {
            dx500Page.clickSectionDX500();
            boolean webStatusBooster = dx500Page.startServer(serverBooster);
            boolean serverStatusBooster = dx500Page.isCheckStartServers(serverBooster);
            if(! webStatusBooster && serverStatusBooster) failedTestWithScreenshot("На сервере " + serverBooster + " запущен. В WEB некорректный статус сервера", true);
            else if(webStatusBooster && ! serverStatusBooster) failedTestWithScreenshot("На сервере " + serverBooster + " не запущен. В WEB некорректно статус сервера", true);
            else if( ! webStatusBooster && ! serverStatusBooster) failedTestWithScreenshot("Cервер " + serverBooster + " не запущен.", true);
        } else failedTestWithScreenshot("Некорректная конфигурация сервера Ассистентов", false);
    }

    @Story(value = "Запуска сервера Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер Пультов провайдера DX500")
    @Order(4)
    @Test
    void test_DX500_Start_Server_Pult() {
        if(dx500Page.isConfigurationServerPult()) {
            dx500Page.clickSectionDX500();
            boolean webStatusPult = dx500Page.startServer(serverPult);
            boolean serverStatusPult = dx500Page.isCheckStartServers(serverPult);
            if(! webStatusPult && serverStatusPult) failedTestWithScreenshot("На сервере " + serverPult + " запущен. В WEB некорректный статус сервера", true);
            else if(webStatusPult && ! serverStatusPult) failedTestWithScreenshot("На сервере " + serverPult + " не запущен. В WEB некорректно статус сервера", true);
            else if( ! webStatusPult && ! serverStatusPult) failedTestWithScreenshot("Cервер " + serverPult + " не запущен.", true);
        }else failedTestWithScreenshot("Некорректная конфигурация сервера Пультов", false);
    }

    @Story(value = "Запуска сервера SIP-Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер SIP-Пультов провайдера DX500")
    @Order(5)
    @Test
    void test_DX500_Start_Server_SIP_Pult() {
        if(dx500Page.isConfigurationSIPPult()) {
            dx500Page.clickSectionDX500();
            boolean webStatusSIPPult = dx500Page.startServer(serverSIPPult);
            boolean serverStatusSIPPult = dx500Page.isCheckStartServers(serverSIPPult);
            if(! webStatusSIPPult && serverStatusSIPPult) failedTestWithScreenshot("На сервере " + serverSIPPult + " запущен. В WEB некорректный статус сервера", true);
            else if(webStatusSIPPult && ! serverStatusSIPPult) failedTestWithScreenshot("На сервере " + serverSIPPult + " не запущен. В WEB некорректно статус сервера", true);
            else if( ! webStatusSIPPult && ! serverStatusSIPPult) failedTestWithScreenshot("Cервер " + serverSIPPult + " не запущен.", true);
        }else failedTestWithScreenshot("Некорректная конфигурация сервера SIP Пульт", false);
    }

    @Story(value = "Сохранение настроек провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно сохранить настройки провайдера DX500")
    @Order(6)
    @Test
    void test_Save_Provider_DX500() {
        if( ! dx500Page.isFormEditProvider().isDisplayed()) dx500Page.clickButtonEditProvider(DX500);
        if( ! dx500Page.clickSaveProvider(DX500)) failedTestWithScreenshot("После сохранения провайдера " + DX500 + "запись в таблице пропала", true);
    }

    @AfterEach
    void tearDown(){
        if(!test){
            if(screenshot) ScreenshotTests.screenshot();
            fail(failedMessage);
        }
        ScreenshotTests.deleteAllScreenshots();
    }

    void failedTestWithScreenshot(String message, boolean screenshot) {
        test = false;
        this.screenshot = screenshot;
        failedMessage = message;
    }
}

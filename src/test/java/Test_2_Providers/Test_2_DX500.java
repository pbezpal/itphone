package Test_2_Providers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureServerTests;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.ProvidersPage;
import RecourcesTests.BeforeEachTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static DataTests.Providers.DataProviderDX500.*;
import static DataTests.Providers.Providers.linkProvidersPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureServerTests
@ExtendWith(TestRules.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_2_DX500 {

    private DX500Page dx500Page;

    @BeforeEach
    void setUp(){
        BeforeEachTests.beforeStartTests();
        if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
    }

    @Story(value = "Добавление провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно добавить провайдер DX500")
    @Order(1)
    @Test
    void test_Add_Provider_DX500() {
        if( ! dx500Page.isCheckProvider(DX500)) assertTrue(dx500Page.addDX500Provider(), "Провайдер DX500 не был добавлен");
        if( ! dx500Page.isMySqlDialplan()) Allure.step("Маршрут для DX500 не был добавлен в БД MySql", Status.BROKEN);
    }

    @Story(value = "Запуска сервера SIP провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер SIP провайдера DX500")
    @Order(2)
    @Test
    void test_Start_DX500_Server_SIP() throws IOException {
        if(dx500Page.isConfigurationServerSIP()) {
            dx500Page.clickSectionDX500();
            boolean webStatusSIP = dx500Page.startServer(serverSIP);
            boolean serverStatusSIP = dx500Page.isCheckStartServers(serverSIP + "-dx");
            if(! webStatusSIP && serverStatusSIP){
                Allure.step("На сервере " + serverSIP + "-dx запущен. В WEB некорректный статус сервера");
                ScreenshotTests.screenshot();
            }else if(! webStatusSIP && serverStatusSIP){
                Allure.step("На сервере " + serverSIP + "-dx не запущен. В WEB некорректно статус сервера");
                ScreenshotTests.screenshot();
            }else if(! webStatusSIP && ! serverStatusSIP){
                Allure.step("Cервер " + serverSIP + "-dx не стартовал.");
                ScreenshotTests.screenshot();
            }
        } else Allure.step("Некорректная конфигурация сервера SIP", Status.BROKEN);
    }


    @Story(value = "Запуска сервера Ассистентов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер Ассистентов провайдера DX500")
    @Order(3)
    @Test
    void test_Start_DX500_Server_Booster() throws IOException {
        if(dx500Page.isConfigurationServerBooster()) {
            dx500Page.clickSectionDX500();
            boolean webStatusBooster = dx500Page.startServer(serverBooster);
            boolean serverStatusBooster = dx500Page.isCheckStartServers(serverBooster);
            if(! webStatusBooster && serverStatusBooster){
                Allure.step("На сервере " + serverBooster + " запущен. В WEB некорректный статус сервера");
                ScreenshotTests.screenshot();
            }else if(webStatusBooster && ! serverStatusBooster){
                Allure.step("На сервере " + serverBooster + " не запущен. В WEB некорректно статус сервера");
                ScreenshotTests.screenshot();
            }else if( ! webStatusBooster && ! serverStatusBooster){
                Allure.step("Cервер " + serverBooster + " не запущен.");
                ScreenshotTests.screenshot();
            }
        } else Allure.step( "Некорректная конфигурация сервера Ассистентов", Status.BROKEN);
    }

    @Story(value = "Запуска сервера Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер Пультов провайдера DX500")
    @Order(4)
    @Test
    void test_Start_DX500_Server_Pult() throws IOException {
        if(dx500Page.isConfigurationServerPult()) {
            dx500Page.clickSectionDX500();
            boolean webStatusPult = dx500Page.startServer(serverPult);
            boolean serverStatusPult = dx500Page.isCheckStartServers(serverPult);
            if(! webStatusPult && serverStatusPult){
                Allure.step("На сервере " + serverPult + " запущен. В WEB некорректный статус сервера");
                ScreenshotTests.screenshot();
            }else if(webStatusPult && ! serverStatusPult){
                Allure.step("На сервере " + serverPult + " не запущен. В WEB некорректно статус сервера");
                ScreenshotTests.screenshot();
            }else if( ! webStatusPult && ! serverStatusPult){
                Allure.step("Cервер " + serverPult + " не запущен.");
                ScreenshotTests.screenshot();
            }
        }else Allure.step("Некорректная конфигурация сервера Пультов", Status.BROKEN);
    }

    @Story(value = "Запуска сервера SIP-Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер SIP-Пультов провайдера DX500")
    @Order(5)
    @Test
    void test_Start_Server_SIP_Pult() throws IOException {
        if(dx500Page.isConfigurationSIPPult()) {
            dx500Page.clickSectionDX500();
            boolean webStatusSIPPult = dx500Page.startServer(serverSIPPult);
            boolean serverStatusSIPPult = dx500Page.isCheckStartServers(serverSIPPult);
            if(! webStatusSIPPult && serverStatusSIPPult){
                Allure.step("На сервере " + serverSIPPult + " запущен. В WEB некорректный статус сервера");
                ScreenshotTests.screenshot();
            }else if(webStatusSIPPult && ! serverStatusSIPPult){
                Allure.step("На сервере " + serverSIPPult + " не запущен. В WEB некорректно статус сервера");
                ScreenshotTests.screenshot();
            }else if( ! webStatusSIPPult && ! serverStatusSIPPult){
                Allure.step("Cервер " + serverSIPPult + " не запущен.");
                ScreenshotTests.screenshot();
            }
        }else Allure.step( "Некорректная конфигурация сервера SIP Пульт", Status.BROKEN);
    }

    @Story(value = "Сохранение настроек провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно сохранить настройки провайдера DX500")
    @Order(6)
    @Test
    void test_Save_Provider_DX500() {
        if( ! dx500Page.isFormEditProvider().isDisplayed()) dx500Page.clickButtonEditProvider(DX500);
        assertTrue(dx500Page.clickSaveProvider(DX500), "После сохранения провайдера " + DX500 + "запись в таблице пропала" );
    }
}

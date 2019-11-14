package TestsServices;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureServerTests;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.KATSPage;
import RecourcesTests.BeforeAllTests;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static DataTests.Providers.DataProviderDX500.*;
import static DataTests.Providers.DataProviderKATS.*;
import static DataTests.Providers.Providers.linkProvidersPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureServerTests
@ExtendWith(BeforeAllTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_2_Providers {

    private DX500Page dx500Page = null;
    private KATSPage katsPage = null;

    @Story(value = "Добавление провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно добавить провайдер DX500")
    @Order(1)
    @Test
    void test_Add_Provider_DX500() {
        if(dx500Page == null) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
        if( ! dx500Page.isCheckProvider(DX500)) assertTrue(dx500Page.addDX500Provider(), "Провайдер DX500 не был добавлен");
        assertTrue(dx500Page.isMySqlDialplan(), "Маршрут для DX500 не был добавлен в БД MySql");
    }

    @Story(value = "Запуска сервера SIP провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер SIP провайдера DX500")
    @Order(2)
    @Test
    void test_Start_DX500_Server_SIP() {
        if(dx500Page == null) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
        assertTrue(dx500Page.isConfigurationServerSIP(), "Некорректная конфигурация сервера SIP");
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.startServer(serverSIP), "Не удалось запустить сервер " + serverSIP);
        assertTrue(dx500Page.isCheckStartServers(serverSIP), "Сервер " + serverSIP + " не запущен");
    }


    @Story(value = "Запуска сервера Ассистентов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер Ассистентов провайдера DX500")
    @Order(3)
    @Test
    void test_Start_DX500_Server_Booster() {
        if(dx500Page == null) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
        assertTrue(dx500Page.isConfigurationServerBooster(), "Некорректная конфигурация сервера Ассистентов");
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.startServer(serverBooster), "Не удалось запустить сервер " + serverBooster);
        assertTrue(dx500Page.isCheckStartServers(serverBooster), "Сервер " + serverBooster + " не запущен");
    }

    @Story(value = "Запуска сервера Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер Пультов провайдера DX500")
    @Order(4)
    @Test
    void test_Start_Server_Pult() {
        if(dx500Page == null) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
        assertTrue(dx500Page.isConfigurationServerPult(), "Некорректная конфигурация сервера Пультов");
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.startServer(serverPult), "Не удалось запустить сервер " + serverPult);
        assertTrue(dx500Page.isCheckStartServers(serverPult), "Сервер " + serverPult + " не запущен");
    }

    @Story(value = "Запуска сервера SIP-Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер SIP-Пультов провайдера DX500")
    @Order(5)
    @Test
    void test_Start_Server_SIP_Pult() {
        if(dx500Page == null) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
        assertTrue(dx500Page.isConfigurationSIPPult(), "Некорректная конфигурация сервера SIP Пульт");
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.startServer(serverSIPPult), "Не удалось запустить сервер " + serverSIPPult);
        assertTrue(dx500Page.isCheckStartServers(serverSIPPult), "Сервер " + serverSIPPult + " не запущен");
    }

    @Story(value = "Сохранение настроек провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно сохранить настройки провайдера DX500")
    @Order(6)
    @Test
    void test_Save_Provider_DX500() {
        if(dx500Page == null) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(linkProvidersPage, DX500);
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.clickSaveProvider(DX500), "После сохранения провайдера " + DX500 + "запись в таблице пропала" );
    }

    @Story(value = "Добавление провайдетра MX1000")
    @Description(value = "Проверяем, что добавляется провайдет MX1000 типа КАТС")
    @Order(7)
    @Test
    void test_Add_Provider_MX1000() {
        if(katsPage == null) katsPage = (KATSPage) MonitoringPage.openSectionWEB(linkProvidersPage, KATS);
        assertTrue(katsPage.isMX1000(), "Сервер MX1000 не установлен на сервер");
        assertTrue(katsPage.addMX1000(MX1000, IPAddress, usernameMX1000, passwordMX1000, dialplanMX1000, delayRegistration), "Не удалось добавить провайдер MX1000");
        assertTrue(katsPage.isSelectProvider(), "Провайдер MX1000 не найден в базе данных MySql");
        assertTrue(katsPage.isSelectDialplan(), "Шаблон номера для MX1000 не найден в базе данных MySql");
    }

    @AfterEach
    void tearDown() throws IOException {
        ScreenshotTests.screenshot();
    }
}

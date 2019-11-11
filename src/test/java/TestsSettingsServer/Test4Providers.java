package TestsSettingsServer;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureServerTests;
import Pages.LoginPage;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.KATSPage;
import Pages.SipServerPage;
import RecourcesTests.BeforeAllTests;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.DataLogin.*;
import static DataTests.DataSipServer.*;
import static DataTests.DataSipServer.turnPortMax;
import static DataTests.Providers.DataProvidersDX500.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureServerTests
@ExtendWith(BeforeAllTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test4Providers {

    private LoginPage loginPage = null;
    private MonitoringPage monitoringPage = null;
    private SipServerPage sipServerPage = null;
    private DX500Page dx500Page = null;
    private boolean statusOpensips = false;
    private boolean moduleStatusSipServer = false;
    private boolean tableStatusSipServer = false;
    private boolean statusSipServer = false;
    private String messageStatusSipServer = "";

    @BeforeEach
    void setUP(){
        if(monitoringPage.isCheckLogin() && ! monitoringPage.isCheckUser(webLoginAdmin)) loginPage = MonitoringPage.clickButtonLogout();
        if(loginPage == null) loginPage = new LoginPage();
        monitoringPage = loginPage.loginOnServer(webLoginAdmin, webPassword);
        if(monitoringPage == null) monitoringPage = new MonitoringPage();
        assertTrue(monitoringPage.isCheckLogin(), "Не удалось авторизоваться на сервере");
    }

    /*@Story(value = "Тестирование настройки SIP-сервера")
    @Description(value = "Проверяем, что через СУ настраивается SIP-сервер, настройки сохраняются на сервере и корректно отображается статус SIP сервера")
    @Order(1)
    @Test
    void test_Settings_SIP_Server() {
        sipServerPage = (SipServerPage) monitoringPage.openSectionWEB(linkSipServerPage);
        assertTrue(sipServerPage.setSettingsSIPServer(urlServer, sipServerPort, turnPortMin, turnPortMax), "Ошибка при настройке SIP сервера");
        assertTrue(sipServerPage.isCheckSettingsSipServer(), "Настройки SIP сервера не сохранились на сервере");
        monitoringPage = sipServerPage.clickButtonMonitoringPage();
        statusOpensips = sipServerPage.isCheckStatusOpensips();
        moduleStatusSipServer = monitoringPage.isCheckModuleStatusSIPServer();
        tableStatusSipServer = monitoringPage.isCheckTableStatusSIPServer();
        if(statusOpensips && moduleStatusSipServer && tableStatusSipServer) statusSipServer = true;
        else if( ! statusOpensips && moduleStatusSipServer && tableStatusSipServer){
            messageStatusSipServer = "На сервере не запущен SIP сервер, однако в СУ отображется статус SIP сервера - ОК";
        }else if(statusOpensips && ! moduleStatusSipServer && tableStatusSipServer) {
            messageStatusSipServer = "На сервере запущен SIP сервер, однако в СУ на 'Холодильнике' отображается некорреткный статус SIP сервера";
        }else if(statusOpensips && moduleStatusSipServer && ! tableStatusSipServer) {
            messageStatusSipServer = "На сервере запущен SIP сервер, однако в СУ в поле 'состояние' отображается некорреткный статус SIP сервера";
        }
        else if( ! statusOpensips && ! moduleStatusSipServer && tableStatusSipServer) {
            messageStatusSipServer = "На сервере не запущен SIP сервер, однако в СУ в поле 'состояние' отображается некорреткный статус SIP сервера";
        }
        else if( ! statusOpensips && moduleStatusSipServer && ! tableStatusSipServer) {
            messageStatusSipServer = "На сервере не запущен SIP сервер, однако в СУ на 'Холодильнике' отображается некорреткный статус SIP сервера";
        }
        else if(statusOpensips && ! moduleStatusSipServer && ! tableStatusSipServer) {
            messageStatusSipServer = "На сервере запущен SIP сервер, однако в СУ отображается некорреткный статус SIP сервера";
        }
        assertTrue(statusSipServer, messageStatusSipServer);
    }*/

    @Story(value = "Тестирование добавление провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно добавить провайдер DX500")
    @Order(1)
    @Test
    void test_Add_Provider_DX500() {
        dx500Page = (DX500Page) monitoringPage.openSectionWEB(linkProvidersPage, DX500);
        if( ! dx500Page.isCheckProvider(DX500)) assertTrue(dx500Page.addDX500Provider(), "Провайдер DX500 не был добавлен");
    }

    @Story(value = "Тестирование запуска сервера SIP провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер SIP провайдера DX500")
    @Order(2)
    @Test
    void test_Start_DX500_Server_SIP() {
        dx500Page = (DX500Page) monitoringPage.openSectionWEB(linkProvidersPage, DX500);
        assertTrue(dx500Page.isConfigurationServerSIP(), "На сервере некорректная конфигурация сервера SIP");
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.startServer(serverSIP), "Не удалось запустить сервер " + serverSIP);
        assertTrue(dx500Page.isCheckStartServersDX500(serverSIP), "Сервер " + serverSIP + " не запущен");
    }


    @Story(value = "Тестирование запуска сервера Ассистентов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер Ассистентов провайдера DX500")
    @Order(3)
    @Test
    void test_Start_DX500_Server_Booster() {
        dx500Page = (DX500Page) monitoringPage.openSectionWEB(linkProvidersPage, DX500);
        assertTrue(dx500Page.isConfigurationServerBooster(), "На сервере некорректная конфигурация сервера Ассистентов");
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.startServer(serverBooster), "Не удалось запустить сервер " + serverBooster);
        assertTrue(dx500Page.isCheckStartServersDX500(serverBooster), "Сервер " + serverBooster + " не запущен");
    }

    @Story(value = "Тестирование запуска сервера Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер Пультов провайдера DX500")
    @Order(4)
    @Test
    void test_Start_Server_Pult() {
        dx500Page = (DX500Page) monitoringPage.openSectionWEB(linkProvidersPage, DX500);
        assertTrue(dx500Page.isConfigurationServerPult(), "На сервере некорректная конфигурация сервера Пультов");
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.startServer(serverPult), "Не удалось запустить сервер " + serverPult);
        assertTrue(dx500Page.isCheckStartServersDX500(serverPult), "Сервер " + serverPult + " не запущен");
    }

    @Story(value = "Тестирование запуска сервера SIP-Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно запустить сервер SIP-Пультов провайдера DX500")
    @Order(5)
    @Test
    void test_Start_Server_SIP_Pult() {
        dx500Page = (DX500Page) monitoringPage.openSectionWEB(linkProvidersPage, DX500);
        assertTrue(dx500Page.isConfigurationSIPPult(), "На сервере некорректная конфигурация сервера SIP Пульт");
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.startServer(serverSIPPult), "Не удалось запустить сервер " + serverSIPPult);
        assertTrue(dx500Page.isCheckStartServersDX500(serverSIPPult), "Сервер " + serverSIPPult + " не запущен");
    }

    @Story(value = "Тестирование сохранение настроек провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно сохранить настройки провайдера DX500")
    @Order(6)
    @Test
    void test_Save_Provider_DX500() {
        dx500Page = (DX500Page) monitoringPage.openSectionWEB(linkProvidersPage, DX500);
        dx500Page.clickSectionDX500();
        assertTrue(dx500Page.clickSaveProvider(DX500), "После сохранения провайдера " + DX500 + "запись в таблице пропала" );
    }
}

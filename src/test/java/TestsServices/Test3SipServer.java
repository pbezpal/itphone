package TestsServices;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureServerTests;
import Pages.LoginPage;
import Pages.MonitoringPage;
import Pages.SipServerPage;
import RecourcesTests.BeforeAllTests;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.DataLogin.*;
import static DataTests.DataSipServer.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureServerTests
@ExtendWith(BeforeAllTests.class)
public class Test3SipServer {
    private LoginPage loginPage = null;
    private MonitoringPage monitoringPage = null;
    private SipServerPage sipServerPage = null;
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

    @Story(value = "Тестирование настройки SIP-сервера")
    @Description(value = "Проверяем, что через СУ настраивается SIP-сервер, настройки сохраняются на сервере и корректно отображается статус SIP сервера")
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
            statusSipServer = false;
            messageStatusSipServer = "На сервере не запущен SIP сервер, однако в СУ отображется статус SIP сервера - ОК";
        }else if(statusOpensips && ! moduleStatusSipServer && tableStatusSipServer) {
            statusSipServer = false;
            messageStatusSipServer = "На сервере запущен SIP сервер, однако в СУ на 'Холодильнике' отображается некорреткный статус SIP сервера";
        }else if(statusOpensips && moduleStatusSipServer && ! tableStatusSipServer) {
            statusSipServer = false;
            messageStatusSipServer = "На сервере запущен SIP сервер, однако в СУ в поле 'состояние' отображается некорреткный статус SIP сервера";
        }
        else if( ! statusOpensips && ! moduleStatusSipServer && tableStatusSipServer) {
            statusSipServer = false;
            messageStatusSipServer = "На сервере не запущен SIP сервер, однако в СУ в поле 'состояние' отображается некорреткный статус SIP сервера";
        }
        else if( ! statusOpensips && moduleStatusSipServer && ! tableStatusSipServer) {
            statusSipServer = false;
            messageStatusSipServer = "На сервере не запущен SIP сервер, однако в СУ на 'Холодильнике' отображается некорреткный статус SIP сервера";
        }
        else if(statusOpensips && ! moduleStatusSipServer && ! tableStatusSipServer) {
            statusSipServer = false;
            messageStatusSipServer = "На сервере запущен SIP сервер, однако в СУ отображается некорреткный статус SIP сервера";
        }
        assertTrue(statusSipServer, messageStatusSipServer);
    }
}

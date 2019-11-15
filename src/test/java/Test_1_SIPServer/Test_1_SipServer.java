package Test_1_SIPServer;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureServerTests;
import Pages.MonitoringPage;
import Pages.SipServerPage;
import RecourcesTests.BeforeEachTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static DataTests.DataLogin.*;
import static DataTests.DataSipServer.*;
import static com.codeborne.selenide.Selenide.refresh;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureServerTests
public class Test_1_SipServer {

    private SipServerPage sipServerPage = null;
    private boolean statusOpensips = false;
    private boolean moduleStatusSipServer = false;
    private boolean tableStatusSipServer = false;
    private boolean statusSipServer = false;
    private String messageStatusSipServer = "";

    @Rule
    TestRules testRules = new TestRules();

    @BeforeEach
    void setUp(){
        BeforeEachTests.beforeStartTests();
    }

    @Story(value = "Тестирование настройки SIP-сервера")
    @Description(value = "Проверяем, что через СУ настраивается SIP-сервер, настройки сохраняются на сервере и корректно отображается статус SIP сервера")
    @Test
    void test_Settings_SIP_Server() {
        sipServerPage = (SipServerPage) MonitoringPage.openSectionWEB(linkSipServerPage);
        assertTrue(sipServerPage.setSettingsSIPServer(urlServer, sipServerPort, turnPortMin, turnPortMax), "Ошибка при настройке SIP сервера");
        assertTrue(sipServerPage.isCheckSettingsSipServer(), "Настройки SIP сервера не сохранились на сервере");
        sipServerPage.clickButtonMonitoringPage();
        statusOpensips = sipServerPage.isCheckStatusOpensips();
        moduleStatusSipServer = MonitoringPage.isCheckModuleStatusSIPServer();
        tableStatusSipServer = MonitoringPage.isCheckTableStatusSIPServer();
        if( ! moduleStatusSipServer || ! tableStatusSipServer){
            refresh();
            statusOpensips = sipServerPage.isCheckStatusOpensips();
            moduleStatusSipServer = MonitoringPage.isCheckModuleStatusSIPServer();
            tableStatusSipServer = MonitoringPage.isCheckTableStatusSIPServer();
        }
        if(statusOpensips && moduleStatusSipServer && tableStatusSipServer) statusSipServer = true;
        else if( ! statusOpensips && moduleStatusSipServer && tableStatusSipServer){
            statusSipServer = false;
            messageStatusSipServer = "На сервере не запущен SIP сервер, однако в СУ отображется статус SIP сервера - ОК";
        }else if(statusOpensips && (! moduleStatusSipServer || ! tableStatusSipServer)) {
            statusSipServer = false;
            messageStatusSipServer = "На сервере запущен SIP сервер, однако в WEB отображается некорреткный статус SIP сервера";
        }
        assertTrue(statusSipServer, messageStatusSipServer);
    }
}

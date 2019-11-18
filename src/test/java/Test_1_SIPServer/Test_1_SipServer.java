package Test_1_SIPServer;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureSIPServerTests;
import Pages.MonitoringPage;
import Pages.SipServerPage;
import RecourcesTests.BeforeEachTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.DataLogin.*;
import static DataTests.DataSipServer.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureSIPServerTests
@ExtendWith(TestRules.class)
public class Test_1_SipServer {

    private SipServerPage sipServerPage = null;

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
    }

}

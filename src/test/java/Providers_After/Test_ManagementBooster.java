package Providers_After;

import AnnotationsTests.Monitoring.EpicMonitoringTests;
import AnnotationsTests.Monitoring.FeatureManagementBooster;
import Pages.MonitoringPage;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static Pages.MonitoringPage.*;
import static Pages.MonitoringPage.isSectionMonitoring;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@EpicMonitoringTests
@FeatureManagementBooster
@ExtendWith(TestRules.class)
public class Test_ManagementBooster {

    private static boolean TEST_STATUS;
    private static String TEST_MESSAGE;
    private static String filename;

    @BeforeEach
    void setUp(){
        StartTests();
        if( ! isSectionMonitoring()) MonitoringPage.clickButtonMonitoringPage();
        if( ! isCheckNotVisibleDownload()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
        if( ! isCheckNotVisibleElement()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
        assertTrue(isSectionMonitoring(), "Не удалось перейти в раздел Мониторинг");
        TEST_STATUS = true;
    }

    @Story(value = "Переход на страницу 'Управление кластерами'")
    @Description(value = "Проверяем, что на странице Мониторинг корректно работает переход на страницу 'Управление кластерами'")
    @Test
    void test_Management_Booster(){
        if( ! clickLinkSimple().isManagementBoosterPage()) fail("Ошибка при переходе на страницу 'Управление кластерами'", new Exception("DOWNLOAD"));
    }

}

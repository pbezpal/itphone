package Providers_After;

import AnnotationsTests.Monitoring.EpicMonitoringTests;
import AnnotationsTests.Monitoring.FeatureManagementBooster;
import Pages.MonitoringPage;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.PROVIDER_DX500.DX500_BOOSTER;
import static HelperClasses.ScreenshotTests.AScreenshot;
import static Pages.MonitoringPage.*;
import static Pages.MonitoringPage.isSectionMonitoring;
import static Pages.Providers.DX500Page.dx500Page;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static com.codeborne.selenide.Selenide.refresh;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@EpicMonitoringTests
@FeatureManagementBooster
public class Test_ManagementBooster {

    private String filename;

    @BeforeEach
    void setUp(){
        StartTests();
        assertTrue(dx500Page.isCheckStartServers(DX500_BOOSTER), "На сервере статус сервера " + DX500_BOOSTER + " - не запущен.");
        if( ! isSectionMonitoring()) MonitoringPage.clickButtonMonitoringPage();
        assertTrue(isCheckNotVisibleDownload(),"Невозможно продолжать тестирование, СУ недоступно");
        assertTrue(isCheckNotVisibleElement(), "Невозможно продолжать тестирование, СУ недоступно");
        assertTrue(isSectionMonitoring(), "Не удалось перейти в раздел Мониторинг");
    }

    @Story(value = "Переход на страницу 'Управление кластерами'")
    @Description(value = "Проверяем, что на странице Мониторинг корректно работает переход на страницу 'Управление кластерами'")
    @Test
    void test_Management_Booster(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTrue(clickLinkSimple().isManagementBoosterPage(), "Ошибка при переходе на страницу 'Управление кластерами'");
    }

    @AfterEach
    void tearDown(){
        AScreenshot(filename);
    }

    @AfterAll
    static void afterAllTests(){
        refresh();
    }

}

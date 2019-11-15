package TestProviders;

import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.KATSPage;
import Pages.Providers.ProvidersPage;
import RecourcesTests.BeforeAllTests;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static DataTests.Providers.DataProviderKATS.*;
import static DataTests.Providers.Providers.linkProvidersPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(BeforeAllTests.class)
public class Test_3_MX1000 {

    private KATSPage katsPage = null;

    @BeforeEach
    void setUp(){
        if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) katsPage = (KATSPage) MonitoringPage.openSectionWEB(linkProvidersPage, KATS);
        if( katsPage == null ) katsPage = KATSPage.getInstance();
    }

    @Story(value = "Добавление провайдетра MX1000")
    @Description(value = "Проверяем, что добавляется провайдет MX1000 типа КАТС")
    @Test
    void test_Add_Provider_MX1000() {
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

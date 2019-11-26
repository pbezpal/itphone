package DX500;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderDX500;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.ProvidersPage;
import RecourcesTests.TestRules;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static DataTests.Providers.PROVIDER_DX500.*;
import static Pages.Providers.DX500Page.dx500Page;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

@EpicServicesTests
@FeatureProviderDX500
@ExtendWith(TestRules.class)
public class Test_DX500_Add {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private boolean screenshot;

    @BeforeEach
    void setUp(){
        StartTests();
        if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, DX500_TYPE_PROVIDER);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        TEST_STATUS = true;
        TEST_MESSAGE = "";
        screenshot = false;
    }

    @Story(value = "Добавление провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно добавить провайдер DX500")
    @Test
    public void test_Add_Provider_DX500() {
        assertTimeout(ofSeconds(600), () -> {
            if (!dx500Page.isCheckProvider(DX500_TYPE_PROVIDER))
                assertTrue(dx500Page.addDX500Provider(), "Провайдер DX500 не был добавлен");
            if (!dx500Page.isMySqlDialplan()) {
                failedTestWithScreenshot("Маршрут для DX500 не был добавлен в БД MySql", false);
            }
        }, () -> "Время теста больше 10 минут");
    }

    @AfterEach
    void tearDown(){
        if( ! TEST_STATUS){
            fail(TEST_MESSAGE, new Exception(String.valueOf(screenshot)));
        }
    }

    void failedTestWithScreenshot(String message, boolean screen) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "\n" + message;
        screenshot = screen;
    }
}

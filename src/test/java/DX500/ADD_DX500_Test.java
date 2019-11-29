package DX500;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderDX500;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static DataTests.Providers.PROVIDER_DX500.*;
import static HelperClasses.ScreenshotTests.AScreenshot;
import static Pages.MonitoringPage.isCheckNotVisibleDownload;
import static Pages.Providers.DX500Page.dx500Page;
import static Pages.Providers.DX500Page.getSectionDX500;
import static Pages.Providers.ProvidersPage.isCheckProviderPage;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static com.codeborne.selenide.Selenide.refresh;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

@EpicServicesTests
@FeatureProviderDX500
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class ADD_DX500_Test {

    private String filename;

    @BeforeEach
    void setUp(){
        StartTests();
        if( ! isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, DX500_TYPE_PROVIDER);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        assertTrue(isCheckNotVisibleDownload(), "Невозможно продолжать тестирование, СУ недоступно");
        assertTrue(isCheckProviderPage().isDisplayed(), "Ошибка при переходе на страницу провайдеров");
        if( ! dx500Page.isFormEditProvider().isDisplayed()) dx500Page.clickButtonAddProvider();
        assertTrue(dx500Page.isFormEditProvider().isDisplayed(), "Форма редактирования провайдера не появилась");
        if( ! getSectionDX500().isDisplayed()) dx500Page.clickSelectTypeProvider(DX500_TYPE_PROVIDER);
        assertTrue(getSectionDX500().isDisplayed(), "Не появился раздел DX500");
    }

    @Story(value = "Сервер SIP(абон)-DX")
    @Description(value = "Добавляем сервер SIP(абон)-DX")
    @Test
    void test_Add_SIP_Abon_DX(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeout(ofSeconds(600), () -> {
            dx500Page.setServer(DX500_SIP_ABON_DX, DX500_SIP_ABON_DX_SMG, DX500_SIP_ABON_DX_CONVERTER_IP);
        }, () -> "Время теста больше 10 минут");
    }

    @Story(value = "Сервер Ассистент")
    @Description(value = "Добавляем сервер Ассистентов")
    @Disabled
    @Test
    void test_Add_Booster(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeout(ofSeconds(600), () -> {
            dx500Page.setServer(DX500_BOOSTER);
        }, () -> "Время теста больше 10 минут");
    }

    @Story(value = "Сервер SIP Пульт")
    @Description(value = "Добавляем сервер SIP Пульт")
    @Test
    void test_Add_SIP_Pult(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeout(ofSeconds(600), () -> {
            dx500Page.setServer(DX500_SIP_PULT, DX500_SIP_PULT_SMG, DX500_SIP_PULT_CONVERTER_IP);
        }, () -> "Время теста больше 10 минут");
    }

    @Story(value = "Сервер Пульт")
    @Description(value = "Добавляем сервер Пульт")
    @Test
    void test_Add_Pult(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeout(ofSeconds(600), () -> {
            dx500Page.setServer(DX500_PULT, DX500_PULT_SMG, DX500_PULT_CONVERTER_IP);
        }, () -> "Время теста больше 10 минут");
    }

    @Story(value = "Сервер Занятости")
    @Description(value = "Добавляем сервер Занятости")
    @Test
    void test_Add_Busy(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeout(ofSeconds(600), () -> {
            dx500Page.setServer(DX500_BUSY);
        }, () -> "Время теста больше 10 минут");
    }

    @Story(value = "Маршрутизация вызовов")
    @Description(value = "Правим маршрутизацию вызовов при добавлении провайдера")
    @Test
    void test_Change_Route_Calls() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeout(ofSeconds(600), () -> {
            dx500Page.setRouteCalls(DX500_DIALPLAN);
        }, () -> "Время теста больше 10 минут");
    }

    @Story(value = "Вводим имя провайдера")
    @Description(value = "Вводим имя провайдера в поле Название")
    @Test
    void test_Change_Name_Provider() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeout(ofSeconds(600), () -> {
            dx500Page.setNameProvider(DX500_TYPE_PROVIDER);
        }, () -> "Время теста больше 10 минут");
    }

    @Story(value = "Добавляем провайдера")
    @Description(value = "Добавляем провайдера и проверяем, что провайдер создался")
    @Test
    void test_Save_Provider() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        dx500Page.clickButtonAddProviders();
        assertTrue(isCheckNotVisibleDownload(), "Невозможно продолжать тестирование, СУ недоступно");
        assertTrue(dx500Page.isCheckProvider(DX500_TYPE_PROVIDER, 60000), "Провайдер не был добавлен");

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

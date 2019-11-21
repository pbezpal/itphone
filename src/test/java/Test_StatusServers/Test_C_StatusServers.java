package Test_StatusServers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureStatusServers;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import RecourcesTests.BeforeAllTests;
import RecourcesTests.TestRules;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.OPENSIPS.OPENSIPS_MODULE_ID;
import static DataTests.OPENSIPS.OPENSIPS_SERVER;
import static DataTests.Providers.PROVIDER_DX500.*;
import static Pages.MonitoringPage.isCheckNotVisibleElement;
import static Pages.MonitoringPage.isSectionMonitoring;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureStatusServers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith({TestRules.class, BeforeAllTests.class})
public class Test_C_StatusServers {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private static final String labelContacts = "сервер контактов:";
    private static final String labelStation = "соединение со станцией:";
    private static final String labelLec = "линейный эхо-компенсатор:";
    private static final String labelBusy = "матрица занятости:";

    @BeforeEach
    void setUp(){
        if( isCheckNotVisibleElement() && ! isSectionMonitoring()) assertTrue(MonitoringPage.clickButtonMonitoringPage(), "Не удалось перейти в раздел Мониторинг");
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
        TEST_STATUS = true;
    }

    @Story(value = "Статус SIP сервера")
    @Description(value = "Проверяем корректное отображение статуса SIP сервера")
    @Test
    void test_Status_SIP_Server(){
        getStatusServer(OPENSIPS_MODULE_ID);
        /*if( ! MonitoringPage.isCheckModuleStatusServer(OPENSIPS_MODULE_ID) || ! MonitoringPage.isCheckTableStatusServer()){
            MonitoringPage.clickUpdateMonitoring();
            getStatusServer(
                    SSHManager.isCheckQuerySSH(OPENSIPS_STATUS),
                    MonitoringPage.isCheckModuleStatusServer(OPENSIPS_MODULE_ID),
                    MonitoringPage.isCheckTableStatusServer(),
                    OPENSIPS_MODULE_ID);
        }*/
    }

    @Story(value = "Статус сервера Ассистентов")
    @Description(value = "Проверяем корректное отображение статуса сервера Ассистентов")
    @Test
    void test_Booster_Status_Server(){
        getStatusServer(DX500_BOOSTER);
        /*if( ! MonitoringPage.isCheckModuleStatusServer(DX500_BOOSTER) || ! MonitoringPage.isStatusService(DX500_BOOSTER)){
            MonitoringPage.clickUpdateMonitoring();
            getStatusServer(
                    DX500Page.isCheckStartServers(DX500_BOOSTER),
                    MonitoringPage.isCheckModuleStatusServer(DX500_BOOSTER),
                    MonitoringPage.isStatusService(DX500_BOOSTER),
                    DX500_BOOSTER);
        }*/
    }

    @Story(value = "Статус сервера контактов на сервере Ассистентов")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере Ассистентов")
    @Test
    void test_Booster_Status_Server_Contacts(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_BOOSTER, BOOSTER_CONNECT_CONTACTS, labelContacts);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }

    @Story(value = "Статус станции на сервере Ассистентов")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Ассистентов")
    @Test
    void test_Booster_Status_Connect_Station(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_BOOSTER, BOOSTER_CONNECT_STATION, labelStation);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }


    @Story(value = "Статус сервера Пультов")
    @Description(value = "Проверяем корректное отображение статуса сервера Пультов")
    @Test
    void test_Pult_Status_Server(){
        getStatusServer(DX500_PULT);
        /*if( ! MonitoringPage.isCheckModuleStatusServer(DX500_PULT) || ! MonitoringPage.isStatusService(DX500_PULT)){
            MonitoringPage.clickUpdateMonitoring();
            getStatusServer(
                    DX500Page.isCheckStartServers(DX500_PULT),
                    MonitoringPage.isCheckModuleStatusServer(DX500_PULT),
                    MonitoringPage.isStatusService(DX500_PULT),
                    DX500_PULT);
        }*/
    }

    @Story(value = "Статус сервера контактов на сервере Пультов")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере Пультов")
    @Test
    void test_Pult_Status_Server_Contacts(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_PULT, PULT_CONNECT_CONTACTS, labelContacts);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }

    @Story(value = "Статус станции на сервере Пультов")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Пультов")
    @Test
    void test_Pult_Status_Connect_Station(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_PULT, PULT_CONNECT_STATION, labelStation);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }

    @Story(value = "Статус конвертера Lan/E1 сервере Пультов")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пультов")
    @Test
    void test_Pult_Status_Converter(){
        getStatusConverter(DX500_PULT, DX500_PULT_SMG);
        /*if( ! MonitoringPage.isCheckModuleConverterLanE1(DX500_PULT) || ! MonitoringPage.isCheckTableConverterLanE1(DX500_PULT)){
            MonitoringPage.clickUpdateMonitoring();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + DX500_PULT_SMG + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(DX500_PULT),
                    MonitoringPage.isCheckTableConverterLanE1(DX500_PULT),
                    DX500_PULT_SMG);
        }*/
    }

    @Story(value = "Статус сервера SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP(абон)-DX")
    @Test
    void test_SIP_Abon_DX_Status_Server(){
        getStatusServer(DX500_SIP_ABON_DX);
        /*if( ! MonitoringPage.isCheckModuleStatusServer(DX500_SIP_ABON_DX) || ! MonitoringPage.isStatusService(DX500_SIP_ABON_DX)){
            MonitoringPage.clickUpdateMonitoring();
            getStatusServer(
                    DX500Page.isCheckStartServers(DX500_SIP_ABON_DX),
                    MonitoringPage.isCheckModuleStatusServer(DX500_SIP_ABON_DX),
                    MonitoringPage.isStatusService(DX500_SIP_ABON_DX),
                    DX500_SIP_ABON_DX);
        }*/
    }

    @Story(value = "Статус конвертера Lan/E1 сервере SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера SIP(абон)-DX")
    @Test
    void test_SIP_Abon_DX_Status_Converter(){
        getStatusConverter(DX500_SIP_ABON_DX, DX500_SIP_ABON_DX_SMG);
        /*if( ! MonitoringPage.isCheckModuleConverterLanE1(DX500_SIP_ABON_DX) || ! MonitoringPage.isCheckTableConverterLanE1(DX500_SIP_ABON_DX)){
            MonitoringPage.clickUpdateMonitoring();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + DX500_SIP_ABON_DX_SMG + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(DX500_SIP_ABON_DX),
                    MonitoringPage.isCheckTableConverterLanE1(DX500_SIP_ABON_DX),
                    DX500_PULT_SMG);
        }*/
    }

    @Story(value = "Статус сервера SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP Пульт")
    @Test
    void test_SIP_Pult_Status_Server(){
        getStatusServer(DX500_SIP_PULT);
        /*if( ! MonitoringPage.isCheckModuleStatusServer(DX500_SIP_PULT) || ! MonitoringPage.isStatusService(DX500_SIP_PULT)){
            MonitoringPage.clickUpdateMonitoring();
            getStatusServer(
                    DX500Page.isCheckStartServers(DX500_SIP_PULT),
                    MonitoringPage.isCheckModuleStatusServer(DX500_SIP_PULT),
                    MonitoringPage.isStatusService(DX500_SIP_PULT),
                    DX500_SIP_PULT);
        }*/
    }

    @Story(value = "Статус сервера контактов на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере SIP ekmn")
    @Test
    void test_SIP_Pult_Status_Server_Contacts(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_SIP_PULT, SIP_PULT_CONNECT_CONTACTS, labelContacts);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }

    @Story(value = "Статус станции на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статус станции на сервере SIP Пульт")
    @Test
    void test_SIP_Pult_Status_Connect_Station(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_SIP_PULT, SIP_PULT_CONNECT_STATION, labelStation);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }

    @Story(value = "Статус линейного эхо-компенсатора на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса линейного эхо-компенсатора на сервере SIP Пульт")
    @Test
    void test_SIP_Pult_Status_Lec(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_SIP_PULT, SIP_PULT_STATUS_LEC, labelLec);
        if(this.TEST_MESSAGE != null) {
            ScreenshotTests.screenshot();
            TEST_STATUS = false;
        }
    }

    @Story(value = "Статус матрицы занятости на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса матрицы занятости на сервере SIP Пульт")
    @Test
    void test_SIP_Pult_Connect_BUSY(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_SIP_PULT, SIP_PULT_CONNECT_BUSY, labelBusy);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }

    @Story(value = "Статус конвертера Lan/E1 сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пульт")
    @Test
    void test_SIP_Pult_Status_Converter(){
        getStatusConverter(DX500_SIP_PULT, DX500_SIP_PULT_SMG);
        /*if( ! MonitoringPage.isCheckModuleConverterLanE1(DX500_SIP_PULT) || ! MonitoringPage.isCheckTableConverterLanE1(DX500_SIP_PULT)){
            MonitoringPage.clickUpdateMonitoring();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + DX500_SIP_PULT_SMG + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(DX500_SIP_PULT),
                    MonitoringPage.isCheckTableConverterLanE1(DX500_SIP_PULT),
                    DX500_SIP_PULT_SMG);
        }*/
    }

    @Story(value = "Статус сервера Занятости")
    @Description(value = "Проверяем корректное отображение статуса сервера Занятости")
    @Test
    void test_Busy_Status_Server(){
        getStatusServer(DX500_BUSY);
        /*if( ! MonitoringPage.isCheckModuleStatusServer(DX500_BUSY) || ! MonitoringPage.isStatusService(DX500_BUSY)){
            MonitoringPage.clickUpdateMonitoring();
            getStatusServer(
                    DX500Page.isCheckStartServers(DX500_BUSY),
                    MonitoringPage.isCheckModuleStatusServer(DX500_BUSY),
                    MonitoringPage.isStatusService(DX500_BUSY),
                    DX500_BUSY);
        }*/
    }

    @Story(value = "Статус сетевого интерфейса на сервере Занятости")
    @Description(value = "Проверяем корректное отображение статус сетевого интерфейса на сервере Занятости")
    @Test
    void test_Busy_Status_Network(){
        this.TEST_MESSAGE = MonitoringPage.isAdapterName(DX500_BUSY);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }

    @Story(value = "Статус станции на сервере Занятости")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Занятости")
    @Test
    void test_Busy_Status_Connect_Station(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_BUSY, BUSY_CONNECT_STATION, labelStation);
        if(this.TEST_MESSAGE != null) TEST_STATUS = false;
    }

    @AfterEach
    void tearDown(){
        assertTrue(TEST_STATUS, TEST_MESSAGE);
    }

    public void getStatusServer(String server){
        boolean serverStatus;
        if(server.equals(OPENSIPS_MODULE_ID)) serverStatus = DX500Page.isCheckStartServers(OPENSIPS_SERVER);
        else serverStatus = DX500Page.isCheckStartServers(server);
        boolean moduleStatusServer = MonitoringPage.isCheckModuleStatusServer(server);
        boolean tableStatusServer = MonitoringPage.isStatusService(server);
        if(serverStatus && moduleStatusServer && tableStatusServer) TEST_STATUS = true;
        else if( ! serverStatus && (moduleStatusServer || tableStatusServer)){
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере не запущена служба " + server + ", однако в СУ отображется статус - ОК";
        }else if(serverStatus && ( ! moduleStatusServer || ! tableStatusServer)) {
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере запущена служба " + server + ", однако в СУ отображается статус - NOK";
        }else{
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере не запущен служба " + server + " и в СУ отображается статус сервера - NOK";
        }
    }

    public void getStatusConverter(String server, String SMG){
        boolean converterStatus = SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMG + " -A 7 | grep 'lapd_ready: establish'");
        boolean moduleStatusConverter = MonitoringPage.isCheckModuleConverterLanE1(server);
        boolean tableStatusConverter = MonitoringPage.isCheckTableConverterLanE1(DX500_SIP_PULT);
        if(converterStatus && moduleStatusConverter && tableStatusConverter) TEST_STATUS = true;
        else if( ! converterStatus && (moduleStatusConverter || tableStatusConverter)){
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На серере статус SMG: " + SMG + " - disconnected, однако в СУ отображется статус SMG - establish";
        }else if(converterStatus && ( ! moduleStatusConverter || ! tableStatusConverter)) {
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере статус SMG: " + SMG + " - establish, однако в СУ отображается статус SMG - disconnected";
        }else{
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере статус SMG: " + SMG + " - disconnected и в СУ отображается статус SMG - disconnected. Проверьте соединение со станцией!!!";
        }
    }
}

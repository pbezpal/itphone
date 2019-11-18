package Test_4_StatusServers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import RecourcesTests.BeforeEachTests;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static DataTests.DataSipServer.commandStatusSIPServer;
import static DataTests.DataSipServer.serverNameModule;
import static DataTests.Providers.DataProviderDX500.*;
import static com.codeborne.selenide.Selenide.refresh;
import static org.junit.jupiter.api.Assertions.fail;

@EpicServicesTests
@Feature(value = "Статусы серверов")
public class Test_4_StatusServers {

    private boolean serverStatus;
    private String messageStatus;

    @BeforeEach
    void setUp(){
        BeforeEachTests.beforeStartTests();
        if( ! MonitoringPage.isSectionMonitoring()) MonitoringPage.clickButtonMonitoringPage();
        serverStatus = false;
        messageStatus = "";
    }

    @Story(value = "Статус SIP сервера")
    @Description(value = "Проверяем корректное отображение статуса SIP сервера")
    @Test
    void test_Status_SIP_Server(){
        getStatusServer(
                SSHManager.isCheckQuerySSH(commandStatusSIPServer),
                MonitoringPage.isCheckModuleStatusServer(serverNameModule),
                MonitoringPage.isCheckTableStatusServer(),
                serverNameModule);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverNameModule) || ! MonitoringPage.isCheckTableStatusServer()){
            refresh();
            getStatusServer(
                    SSHManager.isCheckQuerySSH(commandStatusSIPServer),
                    MonitoringPage.isCheckModuleStatusServer(serverNameModule),
                    MonitoringPage.isCheckTableStatusServer(),
                    serverNameModule);
        }
    }

    @Story(value = "Статус сервера Ассистентов")
    @Description(value = "Проверяем корректное отображение статуса сервера Ассистентов")
    @Test
    void test_Status_server_Booster(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverBooster),
                MonitoringPage.isCheckModuleStatusServer(serverBooster),
                MonitoringPage.isCheckTableStatusServer(serverBooster),
                serverBooster);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverBooster) || ! MonitoringPage.isCheckTableStatusServer(serverBooster)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverBooster),
                    MonitoringPage.isCheckModuleStatusServer(serverBooster),
                    MonitoringPage.isCheckTableStatusServer(serverBooster),
                    serverBooster);
        }
    }

    @Story(value = "Статус сервера Пультов")
    @Description(value = "Проверяем корректное отображение статуса сервера Пультов")
    @Test
    void test_Status_server_Pult(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverPult),
                MonitoringPage.isCheckModuleStatusServer(serverPult),
                MonitoringPage.isCheckTableStatusServer(serverPult),
                serverPult);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverPult) || ! MonitoringPage.isCheckTableStatusServer(serverPult)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverPult),
                    MonitoringPage.isCheckModuleStatusServer(serverPult),
                    MonitoringPage.isCheckTableStatusServer(serverPult),
                    serverPult);
        }
    }

    @Story(value = "Статус конвертера Lan/E1 сервере Пультов")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пультов")
    @Test
    void test_Status_Converter_server_Pult(){
        getStatusConverter(
                SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGPult + " -A 7 | grep 'lapd_ready: establish'"),
                MonitoringPage.isCheckModuleConverterLanE1(serverPult),
                MonitoringPage.isCheckTableConverterLanE1(serverPult),
                SMGPult);
        if( ! MonitoringPage.isCheckModuleConverterLanE1(serverPult) || ! MonitoringPage.isCheckTableConverterLanE1(serverPult)){
            refresh();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGPult + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(serverPult),
                    MonitoringPage.isCheckTableConverterLanE1(serverPult),
                    SMGPult);
        }
    }

    @Story(value = "Статус сервера SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP(абон)-DX")
    @Test
    void test_Status_server_SIP_Abon_DX(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverSIPModule),
                MonitoringPage.isCheckModuleStatusServer(serverSIPModule),
                MonitoringPage.isCheckTableStatusServer(serverSIPModule),
                serverSIPModule);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverSIPModule) || ! MonitoringPage.isCheckTableStatusServer(serverSIPModule)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverSIPModule),
                    MonitoringPage.isCheckModuleStatusServer(serverSIPModule),
                    MonitoringPage.isCheckTableStatusServer(serverSIPModule),
                    serverSIPModule);
        }
    }

    @Story(value = "Статус конвертера Lan/E1 сервере SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера SIP(абон)-DX")
    @Test
    void test_Status_Converter_server_SIP_Abon_DX(){
        getStatusConverter(
                SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGSIP + " -A 7 | grep 'lapd_ready: establish'"),
                MonitoringPage.isCheckModuleConverterLanE1(serverSIPModule),
                MonitoringPage.isCheckTableConverterLanE1(serverSIPModule),
                SMGPult);
        if( ! MonitoringPage.isCheckModuleConverterLanE1(serverSIPModule) || ! MonitoringPage.isCheckTableConverterLanE1(serverSIPModule)){
            refresh();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGSIP + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(serverSIPModule),
                    MonitoringPage.isCheckTableConverterLanE1(serverSIPModule),
                    SMGPult);
        }
    }

    @Story(value = "Статус сервера SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP(абон)-DX")
    @Test
    void test_Status_server_SIP_Pult(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverSIPPult),
                MonitoringPage.isCheckModuleStatusServer(serverSIPPult),
                MonitoringPage.isCheckTableStatusServer(serverSIPPult),
                serverSIPPult);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverSIPPult) || ! MonitoringPage.isCheckTableStatusServer(serverSIPPult)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverSIPPult),
                    MonitoringPage.isCheckModuleStatusServer(serverSIPPult),
                    MonitoringPage.isCheckTableStatusServer(serverSIPPult),
                    serverSIPPult);
        }
    }

    @Story(value = "Статус конвертера Lan/E1 сервере SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера SIP(абон)-DX")
    @Test
    void test_Status_Converter_server_SIP_Pult(){
        getStatusConverter(
                SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGSIPPult + " -A 7 | grep 'lapd_ready: establish'"),
                MonitoringPage.isCheckModuleConverterLanE1(serverSIPPult),
                MonitoringPage.isCheckTableConverterLanE1(serverSIPPult),
                SMGSIPPult);
        if( ! MonitoringPage.isCheckModuleConverterLanE1(serverSIPPult) || ! MonitoringPage.isCheckTableConverterLanE1(serverSIPPult)){
            refresh();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGSIPPult + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(serverSIPPult),
                    MonitoringPage.isCheckTableConverterLanE1(serverSIPPult),
                    SMGSIPPult);
        }
    }

    @AfterEach
    void tearDown(){
        if( ! serverStatus) {
            ScreenshotTests.screenshot();
            fail(messageStatus);
        }
    }

    public void getStatusServer(boolean serverStatus, boolean moduleStatusServer, boolean tableStatusServer, String server){
        if(serverStatus && moduleStatusServer && tableStatusServer) serverStatus = true;
        else if( ! serverStatus && (moduleStatusServer || tableStatusServer)){
            this.serverStatus = false;
            this.messageStatus = "На сервере не запущен сервер " + server + ", однако в СУ отображется статус сервера Ассистентов - ОК";
        }else if(serverStatus && ( ! moduleStatusServer || ! tableStatusServer)) {
            this.serverStatus = false;
            this.messageStatus = "На сервере запущен сервер " + server + ", однако в WEB отображается некорреткный статус SIP сервера";
        }else{
            this.serverStatus = false;
            this.messageStatus = "На сервере не запущен сервер " + server + " и в WEB отображается некорреткный статус SIP сервера";
        }
    }

    public void getStatusConverter(boolean serverStatus, boolean moduleStatusConverter, boolean tableStatusConverter, String SMG){
        if(serverStatus && moduleStatusConverter && tableStatusConverter) serverStatus = true;
        else if( ! serverStatus && (moduleStatusConverter || tableStatusConverter)){
            this.serverStatus = false;
            this.messageStatus = "На сервере у SMG: " + SMG + " статус - disconnected, однако в СУ отображется статус SMG - establish";
        }else if(serverStatus && ( ! moduleStatusConverter || ! tableStatusConverter)) {
            this.serverStatus = false;
            this.messageStatus = "На сервере у SMG: " + SMG + " статус establish, однако в WEB отображается статус SMG - disconnected";
        }else{
            this.serverStatus = false;
            this.messageStatus = "На сервере для SMG: " + SMG + " статус - disconnected и в WEB отображается статус SMG - disconnected";
        }
    }
}

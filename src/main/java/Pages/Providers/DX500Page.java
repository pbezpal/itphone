package Pages.Providers;

import HelperClasses.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.DataLogin.urlServer;
import static DataTests.Providers.DataProviderDX500.*;
import static com.codeborne.selenide.Selenide.$;

public class DX500Page extends ProvidersPage implements IProvidersPage{

    public static DX500Page dx500Page = new DX500Page();
    public static DX500Page getInstance() {return dx500Page;}

    public boolean addDX500Provider(){
        isCheckProviderPage();
        $("#add_provider").click();
        isFormEditProvider();
        clickSelectTypeProvider(DX500);
        addServers();
        setNameProvider(DX500);
        setRouteCalls(dialplanDX500);
        clickButtonAddProviders();

        return isCheckProvider(DX500, 60000);
    }

    @Step(value = "Создаем сервер {serverName}")
    public DX500Page addServer(String serverName){
        if (serverName.equals(serverSIP)) serverName = nameSIP;
        $("#sel_add_new_server_to_prov").click();
        $("#sel_add_new_server_to_prov").selectOptionByValue(serverName);
        $("#btn_add_new_server_to_prov").click();

        return this;
    }

    @Step(value = "Переходим в раздел сервера {serverName}")
    public DX500Page clickSectionServer(String serverName){
        if($("li[aria-controls*=" + serverName + "]").attr("aria-selected").equals("false")) {
            $("li[aria-controls*=" + serverName + "]").click();
        }
        return this;
    }

    @Step(value = "Нажимаем кнопку 'Добавить SMG' на сервере {serverName}")
    public DX500Page clickButtonAddSMG(String serverName){
        $("form[id*='" + serverName + "']").find("button.btn-dx500-add-smg").click();
        return this;
    }

    @Step(value = "Вводим номер SMG - {numberSMG} для сервера {serverName}")
    public DX500Page sendInputSMG(String serverName, String numberSMG){
        if( ! $("form[id*='" + serverName + "']").find(By.name("smg")).val().equals(numberSMG)) {
            $("form[id*='" + serverName + "']").find(By.name("smg")).clear();
            $("form[id*='" + serverName + "']").find(By.name("smg")).sendKeys(numberSMG);
        }
        return this;
    }

    @Step(value = "Настраиваем тип конвертера для сервера {serverName}")
    public DX500Page selectTypeConverter(String serverName){
        $("form[id*='" + serverName + "']").find(By.name("smg_converter")).selectOptionByValue("Конвертер");
        return this;
    }

    @Step(value = "Вводим IP адрес конвертера - {converterIP} для сервера {serverName}")
    public DX500Page sendInputConverterIP(String serverName, String converterIP){
        if( ! $("form[id*='" + serverName + "']").find(By.name("conv_ip")).val().equals(converterIP)) {
            $("form[id*='" + serverName + "']").find(By.name("conv_ip")).clear();
            $("form[id*='" + serverName + "']").find(By.name("conv_ip")).setValue(converterIP);
        }
        return this;
    }

    @Step(value = "Выбираем IP адрес шлюза для сервера {serverName}")
    public DX500Page selectGateIP(String serverName){
        $("form[id*='" + serverName + "']").find(By.name("gate_ip")).selectOptionByValue(urlServer);
        return this;
    }

    public DX500Page setServer(String serverName, String numberSMG, String converterIP){
        if( ! $("#provider_dx500_tab_elem").find("a[id*='" + serverName + "']").isDisplayed()) addServer(serverName);
        if(serverName.equals(nameSIP)) serverName = serverSIP;
        clickSectionServer(serverName);
        clickButtonAddSMG(serverName);
        sendInputSMG(serverName, numberSMG);
        selectTypeConverter(serverName);
        sendInputConverterIP(serverName, converterIP);
        selectGateIP(serverName);
        return this;
    }

    public DX500Page setServer(){
        if( ! $("#provider_dx500_tab_elem").find("a[id*='" + serverBooster + "']").isDisplayed()) addServer(serverBooster);
        clickSectionServer(serverBooster);
        $("form[id*='" + serverBooster + "']").find(By.name("ip")).clear();
        $("form[id*='" + serverBooster + "']").find(By.name("ip")).sendKeys(dbIP);
        $("form[id*='" + serverBooster + "']").find(By.name("adapter_name")).selectOptionByValue(adapterName);
        $("form[id*='" + serverBooster + "']").find(By.name("station")).sendKeys(numberStation);
        $("form[id*='" + serverBooster + "']").find(By.name("gate_ip")).sendKeys(gateIP);
        $("form[id*='" + serverBooster + "']").find(By.name("gate_port")).sendKeys(gatePort);
        return this;
    }

    public DX500Page addServers(){
        setServer(serverSIP, SMGSIP, IPConverterSIP);
        setServer();
        setServer(serverPult, SMGPult, IPConverterPult);
        setServer(serverSIPPult, SMGSIPPult, IPConverterSIPPult);
        return this;
    }

    @Step(value = "Переходим на вкладку DX500")
    public DX500Page clickSectionDX500(){
        isCheckProviderPage();
        clickButtonEditProvider(DX500);
        $("#provider_dialog_params").waitUntil(Condition.visible, 60000);
        $("li[aria-controls='dx500_settings']").click();
        return this;
    }

    @Step(value = "Запускаем сервер {serverName}")
    public boolean startServer(String serverName){
        clickSectionServer(serverName);
        if($("form[id*='" + serverName + "']").find("i.button-control-status").text().equals("Отключен")) {
            $("form[id*='" + serverName + "']").find("i.button-control-start").click();
        }

        try{
            $("form[id*='" + serverName + "']").find("i.button-control-status-start").waitUntil(Condition.visible, 60000);
        }
        catch (ElementNotFound elementNotFound) {
            return false;
        }
        return true;
    }

    /***** Проверка конфигурации серверов DX500 *****/

    @Step(value = "Проверяем конфигурацию сервера Ассистентов")
    public boolean isConfigurationServerBooster(){
        if(SSHManager.isCheckQuerySSH(boosterDBIP) && SSHManager.isCheckQuerySSH(boosterDBPort)
                && SSHManager.isCheckQuerySSH(boosterAdapterName) && SSHManager.isCheckQuerySSH(boosterStation)
                && SSHManager.isCheckQuerySSH(boosterIP) && SSHManager.isCheckQuerySSH(boosterPort)) return true;

        return false;
    }

    @Step(value = "Проверка конфигурации сервера Пультов")
    public boolean isConfigurationServerPult(){
        if(SSHManager.isCheckQuerySSH(pultContactIP) && SSHManager.isCheckQuerySSH(pultContactPort)
                && SSHManager.isCheckQuerySSH(pultSGP) && SSHManager.isCheckQuerySSH(pultIP)
                && SSHManager.isCheckQuerySSH(pultPort) && SSHManager.isCheckQuerySSH(pultMGP)
                && SSHManager.isCheckQuerySSH(SMG1_Enable) && SSHManager.isCheckQuerySSH(SMG1_SG1DEV)
                && SSHManager.isCheckQuerySSH(SMG1_MG1DEV)) return true;

        return false;
    }

    @Step(value = "Проверка конфигурации сервера SIP")
    public boolean isConfigurationServerSIP(){
        if(SSHManager.isCheckQuerySSH(sipIP) && SSHManager.isCheckQuerySSH(sipPort)
                && SSHManager.isCheckQuerySSH(sipDBIP) && SSHManager.isCheckQuerySSH(sipDPort)
                && SSHManager.isCheckQuerySSH(sipSGI) && SSHManager.isCheckQuerySSH(sipSGP)
                && SSHManager.isCheckQuerySSH(sipMGI) && SSHManager.isCheckQuerySSH(sipMGP)
                && SSHManager.isCheckQuerySSH(SMG0_Enable) && SSHManager.isCheckQuerySSH(SMG0_SG0DEV)
                && SSHManager.isCheckQuerySSH(SMG0_MG0DEV)) return true;

        return false;
    }

    @Step(value = "Проверка конфигурации сервера SIP Пульт")
    public boolean isConfigurationSIPPult(){
        if(SSHManager.isCheckQuerySSH(sipPultSGPort) && SSHManager.isCheckQuerySSH(sipPultIP)
                && SSHManager.isCheckQuerySSH(sipPultPort) && SSHManager.isCheckQuerySSH(sipPultDBIP)
                && SSHManager.isCheckQuerySSH(sipPultDBPort) && SSHManager.isCheckQuerySSH(sipPultMPPort)
                && SSHManager.isCheckQuerySSH(sipPultRingin) && SSHManager.isCheckQuerySSH(MSG3_Enable)
                && SSHManager.isCheckQuerySSH(SMG3_SG3DEV) && SSHManager.isCheckQuerySSH(SMG3_MG3DEV)) return true;

        return false;
    }

    @Step(value = "Проверяем, что в БД записался диалплан для DX500")
    public boolean isMySqlDialplan(){ return SSHManager.isCheckQuerySSH(mysqlDialplan); }
}
